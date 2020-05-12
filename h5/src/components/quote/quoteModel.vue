<template>
  <div>
    <!--title显示信息  -->
    <div class="titleWrap">
      <div class="title">
        报价模板列表：
      </div>
      <div class="titleRight">
        <div class="count">
          共有信息 <span class="font_red font_blod font_mr">{{total}}</span>条
        </div>
      </div>
      <div class="add putHover" @click="addModel">
        新增模板<img src="@/assets/images/add.png" alt="">
      </div>
    </div>
    <!-- list内容 -->
    <li  v-if='total<=0' class="nodata shadow">
          暂无数据
    </li>
    <div v-else v-for="(item,i) in dataList" :key="i" class="listWrap shadow">
        <ul class="list">
            <li class="li_div ">项目名</li>
        </ul>
        <ul class="list">
            <li v-for="(objs,index) in JSON.parse(item.contains)" :key="index" class="li_div right_border">{{objs.name}}</li>
            <li class="li_div right_border">报价小计</li>
            <li class="li_div right_border">系数</li>
            <li class="li_div right_border">实际小计</li>
        </ul>
        <ul class="list">

        </ul>
        <ul class="list">
            <li class="li_div right_border">报价合计</li>
            <li class="total right_border"></li>
            <li class="li_div right_border">实际合计</li>
            <li class="total "></li>
        </ul>
        <ul class="operate">
            <span class="font_mr edit putHover" @click="editModel(item.id,item.contains)">修改</span>
            <span class="font_mr delete putHover" @click="deleteModel(item.id)">删除</span>
            <span class="font_mr toHistory putHover"  @click="toQuote(item.id,item.contains)">用此模板去报价</span>
        </ul>
    </div>
     
    <div class="pageWrap">
          <Page :total="total" :current="pageNo" :page-size='pageSize' @on-change = 'changePage' prev-text="上一页" next-text="下一页" />
      </div>
    <!-- 弹框区 -->
    <div class="modelWrap">
      <Modal class="addModel" :closable="false" :mask-closable="false" v-model="addQuoteModel" width="360">
        <p class="modelTitle" slot="header">
            新增报价模板
        </p>
        <div class="modelContent">
            <CheckboxGroup v-model="addList">
                <div class="addName">
                  <p >必填：</p>
                </div>
                <div class="addName">
                  <Checkbox class="item5" label="meterial" disabled>材料名称</Checkbox>
                  <Checkbox class="item5" label="supplier" disabled>供应商</Checkbox>
                </div>
                <div class="addName">
                  <Checkbox class="item5" label="price" disabled>价格</Checkbox>
                  <Checkbox class="item5" label="count" disabled>数量</Checkbox>
                </div>
                <div class="addName">
                  <p>选填：</p>
                </div>
                <div class="addName">
                  <Checkbox class="item5" label="supplierUser" >联系人</Checkbox>
                  <Checkbox class="item5" label="supplierPhone" >联系电话</Checkbox>
                </div>
                <div class="addName">
                  <Checkbox class="item5" label="unit" >计量单位</Checkbox>
                  <Checkbox class="item5" label="address" >详细地址</Checkbox>
                </div>
            </CheckboxGroup>
            
        </div>
        <div slot="footer">
          <div class="modelBtnWraP">
            <span class="cancel" @click="addModelCancel">取消</span>
            <span class="confirm" @click="addModelConfirm">确认</span>
          </div>
        </div>
      </Modal>
      <Modal class="addModel" :closable="false" :mask-closable="false" v-model="editQuoteModel" width="360">
        <p class="modelTitle" slot="header">
            新增报价模板
        </p>
        <div class="modelContent">
            <CheckboxGroup v-model="editList">
                <div class="addName">
                  <p >必填：</p>
                </div>
                <div class="addName">
                  <Checkbox class="item5" label="meterial" disabled>材料名称</Checkbox>
                  <Checkbox class="item5" label="supplier" disabled>供应商</Checkbox>
                </div>
                <div class="addName">
                  <Checkbox class="item5" label="price" disabled>价格</Checkbox>
                  <Checkbox class="item5" label="count" disabled>数量</Checkbox>
                </div>
                <div class="addName">
                  <p>选填：</p>
                </div>
                <div class="addName">
                  <Checkbox class="item5" label="supplierUser" >联系人</Checkbox>
                  <Checkbox class="item5" label="supplierPhone" >联系电话</Checkbox>
                </div>
                <div class="addName">
                  <Checkbox class="item5" label="unit" >计量单位</Checkbox>
                  <Checkbox class="item5" label="address" >详细地址</Checkbox>
                </div>
            </CheckboxGroup>
            
        </div>
        <div slot="footer">
          <div class="modelBtnWraP">
            <span class="cancel" @click="editModelCancel">取消</span>
            <span class="confirm" @click="editModelConfirm">确认</span>
          </div>
        </div>
      </Modal>
      <Modal class="addModel" :closable="false" :mask-closable="false" v-model="QuoteModel" width="1100">
        <p class="modelTitle" slot="header">
            填写报价信息
        </p>
        <div class="modelContent">
          <div class="type_content">
              <span class="input_name">项目名称：</span>
              <Input class="model_input" type="text" placeholder="请输入项目名称" :maxlength="9"  v-model="projectName" />
          </div>
          <div class="listWrap shadow">
            <ul class="list">
                <li v-for="(objs,index) in qouteList" :key="index" class="li_div right_border">{{objs.name}}</li>
                <li class="li_div right_border">报价小计</li>
                <li class="li_div right_border">系数</li>
                <li class="li_div right_border">实际小计</li>
                <li class="li_div ">状态</li>

            </ul>
            <ul class="list" v-for="(item,i) in qouteModelList" :key="i">
              <li  class="li_div right_border">
                <span >{{item.meterial}}</span>
              </li>
              <li  class="li_div right_border">
                <span >{{item.supplier}}</span>
              </li>
              <li  class="li_div right_border">
                <span >{{item.price}}</span>
              </li>
              <li  class="li_div right_border">
                <span >{{item.count}}</span>
              </li>
              <li v-if="item.supplierUser !=null" class="li_div right_border">
                <span >{{item.supplierUser}}</span>
              </li>
              <li v-if="item.supplierPhone !=null"  class="li_div right_border">
                <span >{{item.supplierPhone}}</span>
              </li>
              <li v-if="item.unit !=null" class="li_div right_border">
                <span >{{item.unit}}</span>
              </li>
              <li v-if="item.address !=null" class="li_div right_border">
                <span >{{item.address}}</span>
              </li>
              <li  class="li_div right_border">{{item.qouteNum}}</li>
              <li  class="li_div right_border">
                <span >{{item.ratio}}</span>
              </li>
              <li v-if="item" class="li_div right_border">{{item.factNum}}</li>
              <li class="li_div ">
                <span>已加入</span>
              </li>
            </ul>
            <ul class="list" v-if='isShowNew'>
              <li v-if="projectList " class="li_div right_border">
                <Select  placeholder="请选择物料名称" @on-change='loadSupplier(projectList.meterial)' v-model="projectList.meterial" size="small" style="width:90px">
                  <Option v-for="item in materialList"  :value="item.materialName" :key="item.materialId">{{ item.materialName }}</Option>
                </Select>
              </li>
              <li v-if="projectList " class="li_div right_border">
                <Select  placeholder="请选择供应商" @on-open-change = "openSupplier"  v-model="projectList.supplier" size="small" style="width:90px">
                  <Option v-for="item in supplierList" :value="item.supplierName" :key="item.supplierId">{{ item.supplierName }}</Option>
                </Select>
              </li>
              <li v-if="projectList " class="li_div right_border">
                <Input placeholder="请输入价格" class="addName_input" type="number"  v-model="projectList.price"  />
              </li>
              <li v-if="projectList " class="li_div right_border">
                <Input placeholder="请输入数量"  class="addName_input"  v-model="projectList.count" type="number" />
              </li>
              <li v-if="projectList && projectList.supplierUser !=null" class="li_div right_border">
                <Input placeholder="请输入联系人" class="addName_input"  v-model="projectList.supplierUser"  />
              </li>
              <li v-if="projectList && projectList.supplierPhone !=null" class="li_div right_border">
                <Input  placeholder="请输入联系方式" class="addName_input"  v-model="projectList.supplierPhone"  />
              </li>
              <li v-if="projectList && projectList.unit !=null" class="li_div right_border">
                <Input  placeholder="请输入计量单位" class="addName_input"  v-model="projectList.unit"  />
              </li>
              <li v-if="projectList && projectList.address !=null" class="li_div right_border">
                <Input  placeholder="请输入详细地址" class="addName_input"  v-model="projectList.address"  />
              </li>
              <li v-if="projectList" class="li_div right_border">{{projectList.price*projectList.count}}</li>
              <li v-if="projectList" class="li_div right_border">
                <Input  placeholder="请输入系数" class="addName_input"  v-model="projectList.ratio" type="number" />
              </li>
              <li v-if="projectList" class="li_div right_border">{{(projectList.price*projectList.count*projectList.ratio)?(projectList.price*projectList.count*projectList.ratio):0}}</li>
              <li class="li_div ">
                <span style="color:blue" class="putHover" @click="qouteAdd()">加入</span>
              </li>
            </ul>
            <ul class="list">
              <img @click="toAddNewName" class="putHover" src="@/assets/images/add.png" alt="">
            </ul>
            <ul class="list">
                <li class="li_div right_border">报价合计</li>
                <li class="total right_border">{{totalOld}}</li>
                <li class="li_div right_border">实际合计</li>
                <li class="total ">{{totalNew}}</li>

            </ul>
          </div>
            
        </div>
        <div slot="footer">
          <div class="modelBtnWraP">
            <span class="cancel" @click="quoteCancel">取消</span>
            <span class="confirm" @click="quoteConfirm">确认</span>
          </div>
        </div>
      </Modal>
    </div>
  </div>
</template>

<script>
export default {
  name: 'material',
  data () {
    return {
      dataList:[],
      materialList:[],
      supplierList:[],
      qouteModelList:[],//最终的
      addQuoteModel:false,
      addList:['meterial','supplier','price','count'],
      editList:[],
      editId:'',
      editQuoteModel:false,
      total:0,
      pageSize:5,
      pageNo:1,
      QuoteModel:false,
      projectName:'',
      qouteList:[],
      qouteId:'',
      projectList:{},
      isShowNew:false,
      totalOld:0,
      totalNew:0,
    }
  },
  components:{
  },
  methods:{
    //新增报价模板
    addModel(){
      this.addQuoteModel = true;
    },
    //取消新增模板
    addModelCancel(){
      this.addQuoteModel = false;
      this.addList = ['meterial','supplier','price','count'];
    },
    //确认新增模板
    addModelConfirm(){
      let arrList = []
      let obj = {}
      for( let i in this.addList){
        switch(this.addList[i]) {
          case 'meterial':
           obj ={
              value:'meterial',
              name:'物料名称'
            }
            arrList.push(obj)
            break;
          case 'supplier':
            obj ={
              value:'supplier',
              name:'供应商'
            }
            arrList.push(obj)
            break;
          case 'price':
            obj ={
              value:'price',
              name:'价格'
            }
            arrList.push(obj)
            break;
          case 'count':
            obj ={
              value:'count',
              name:'数量'
            }
            arrList.push(obj)
            break; 
          case 'supplierUser':
            obj ={
              value:'supplierUser',
              name:'联系人'
            }
            arrList.push(obj)
            break;
          case 'supplierPhone':
            obj ={
              value:'supplierPhone',
              name:'联系方式'
            }
            arrList.push(obj)
            break;
          case 'unit':
            obj ={
              value:'unit',
              name:'计量单位'
            }
            arrList.push(obj)
            break;
          case 'address':
            obj ={
              value:'address',
              name:'详细地址'
            }
            arrList.push(obj)
            break;      
          default:
      } 
      }
      let $this = this
      this.$axios({
        method:'post',
        url:'template/save',
        data:$this.qs.stringify({    //这里是发送给后台的数据
            contains:JSON.stringify(arrList)
        })
      }).then((response) =>{
        if(response.data.code =='200'){
          this.addQuoteModel = false;
          this.addList = ['meterial','supplier','price','count'];
          this.loadData()
        }else{
          this.$Message.error(response.data.msg);
        }
      })
      
    },
    //修改报价模板
    editModel(id,arr){
      this.editId  = id
      this.editQuoteModel = true;
      let editArr = JSON.parse(arr);
      for(let i in editArr){
        this.editList.push(editArr[i].value)
      }
    },
    //取消修改模板
    editModelCancel(){
      this.editQuoteModel = false;
      this.editList = [];
      this.editId = ''
    },
    //确认修改模板
    editModelConfirm(){
      let arrList = []
      let obj = {}
      for( let i in this.editList){
        switch(this.editList[i]) {
          case 'meterial':
           obj ={
              value:'meterial',
              name:'物料名称'
            }
            arrList.push(obj)
            break;
          case 'supplier':
            obj ={
              value:'supplier',
              name:'供应商'
            }
            arrList.push(obj)
            break;
          case 'price':
            obj ={
              value:'price',
              name:'价格'
            }
            arrList.push(obj)
            break;
          case 'count':
            obj ={
              value:'count',
              name:'数量'
            }
            arrList.push(obj)
            break; 
          case 'supplierUser':
            obj ={
              value:'supplierUser',
              name:'联系人'
            }
            arrList.push(obj)
            break;
          case 'supplierPhone':
            obj ={
              value:'supplierPhone',
              name:'联系方式'
            }
            arrList.push(obj)
            break;
          case 'unit':
            obj ={
              value:'unit',
              name:'计量单位'
            }
            arrList.push(obj)
            break;
          case 'address':
            obj ={
              value:'address',
              name:'详细地址'
            }
            arrList.push(obj)
            break;      
          default:
      } 
      }
      let $this = this
      this.$axios({
        method:'post',
        url:'template/update',
        data:$this.qs.stringify({    //这里是发送给后台的数据
            id:this.editId,
            contains:JSON.stringify(arrList)
            
        })
      }).then((response) =>{
        if(response.data.code =='200'){
          this.loadData()
          this.editQuoteModel = false;
          this.editId = ''
        }else{
          this.$Message.error(response.data.msg);
        }
      })
      
    },
    changePage(e){
      this.pageNo = e
      this.loadData()
    },
    deleteModel(id){
      let $this = this
      this.$axios({
        method:'post',
        url:'template/deleteById',
        data:$this.qs.stringify({    //这里是发送给后台的数据
            id:id,
        })
      }).then((response) =>{
        if(response.data.code =='200'){
          this.$Message.success(response.data.msg);
          this.loadData()
        }else{
          this.$Message.error(response.data.msg);
        }
      })
    },
    loadData(){
      let $this = this
      this.$axios({
          method:'get', 
          url:'template/page',
          params:{    //这里是发送给后台的数据
              pageIndex:this.pageNo,
              limit:this.pageSize
          }
      }).then((response) =>{
        if(response.data.code =='200'){
          this.dataList = response.data.pagination.datas
          this.total = (response.data.pagination.total && response.data.pagination)?response.data.pagination.total:0
        }else{
          this.$Message.error(response.data.msg);
        }
      })

    },
    toQuote(id,arr){
      this.qouteModelList=[]
      this.projectName = ''
      this.projectList = {}
      this.QuoteModel = true
      this.isShowNew = true
      this.qouteId  = id
      this.qouteList = JSON.parse(arr);
      let data = {}
      for(let i in this.qouteList){
        if(this.qouteList[i].value == 'meterial'){
          data.meterial = ''
        }else if(this.qouteList[i].value == 'supplier'){
          data.supplier = ''
        }else if(this.qouteList[i].value == 'price'){
          data.price = ''
        }else if(this.qouteList[i].value == 'count'){
          data.count = ''
        }else if(this.qouteList[i].value == 'supplierUser'){
          data.supplierUser = ''
        }else if(this.qouteList[i].value == 'supplierPhone'){
          data.supplierPhone = ''
        }else if(this.qouteList[i].value == 'unit'){
          data.unit = ''
        }else if(this.qouteList[i].value == 'address'){
          data.address = ''
        }
        
        
      }
      data.ratio = 1
      this.projectList = data;
      
      console.log( this.projectList)
      

    },
    qouteAdd(){
      for(var key in this.projectList){
        if(!this.projectList[key]) {
          this.$Message.error(
            {
              content: ' 每一项加入时都不能为空',
              duration: 2000
            }
          );
          return;
        }
      }
      this.isShowNew = false
      let data = this.projectList
      data.qouteNum = data.price*data.count
      data.factNum = data.price*data.count*data.ratio
      this.qouteModelList.push(data)
      this.totalOld = this.totalOld+data.qouteNum
      this.totalNew = this.totalNew+data.factNum
      this.projectList = {}
    },
    quoteCancel(){
      this.QuoteModel = false
      this.qouteId  = ''
      this.qouteList = [];
      this.projectList = {}
      this.totalOld = 0
      this.totalNew = 0
      this.qouteModelList=[]
    },
    quoteConfirm(){
      if(!this.projectName){
        this.$Message.error(
          {
            content: '项目名不能为空',
            duration: 2000
          }
        );
        return;
      }
      if(this.qouteModelList.length<=0){
        this.$Message.error(
          {
            content: '报价内容不能为空',
            duration: 2000
          }
        );
        return
      }
      if(this.totalOld<=0){
        this.$Message.error(
          {
            content: '报价总计不能0',
            duration: 2000
          }
        );
        return
      }
      if(this.totalNew<=0){
        this.$Message.error(
          {
            content: '实际报价总计不能0',
            duration: 2000
          }
        );
        return
      }
      let $this = this
      this.$axios({
        method:'post',
        url:'quote/save',
        data:$this.qs.stringify({    //这里是发送给后台的数据
            templateId:this.qouteId,
            projectName:this.projectName,
            totalOld : this.totalOld,
            totalNew : this.totalNew,
            contains:JSON.stringify(this.qouteModelList) 

        })
      }).then((response) =>{
        if(response.data.code =='200'){
          this.$Message.success(response.data.msg);
          this.QuoteModel = false
          this.qouteId  = ''
          this.qouteList = [];
          this.totalOld = 0
          this.totalNew = 0
        }else{
          this.$Message.error(response.data.msg);
        }
      })
      
    },
    loadMaterial(){
      let $this = this
      this.$axios({
          method:'get', url:'material/list',
      }).then((response) =>{
        if(response.data.code =='200'){
          this.materialList = response.data.data
        }else{
          this.$Message.error(response.data.msg);
        }
      })
    },
    openSupplier(){
      if(!this.projectList.meterial){
         this.$Message.error('请先选择物料名称！');
      }
    },
    loadSupplier(id){
      let name = ''
      for( let i in this.materialList){
        if(id == this.materialList[i].materialName){
          name = this.materialList[i].materialName
        }

      }
      if(!name){
        return
      }
      this.supplierList = []
      let $this = this
      this.$axios({
          method:'get', 
          url:'material/getSuppListByMaterialName',
          params:{    //这里是发送给后台的数据
              materialName:name
          }
      }).then((response) =>{
        if(response.data.code =='200'){
          this.supplierList = response.data.data
        }else{
          this.$Message.error(response.data.msg);
        }
      })
    },
    toAddNewName(){
      this.isShowNew = true;
      this.projectList ={}
      let data = {}
      for(let i in this.qouteList){
        if(this.qouteList[i].value == 'meterial'){
          data.meterial = ''
        }else if(this.qouteList[i].value == 'supplier'){
          data.supplier = ''
        }else if(this.qouteList[i].value == 'price'){
          data.price = ''
        }else if(this.qouteList[i].value == 'count'){
          data.count = ''
        }else if(this.qouteList[i].value == 'supplierUser'){
          data.supplierUser = ''
        }else if(this.qouteList[i].value == 'supplierPhone'){
          data.supplierPhone = ''
        }else if(this.qouteList[i].value == 'unit'){
          data.unit = ''
        }else if(this.qouteList[i].value == 'address'){
          data.address = ''
        }
        
        
      }
      data.ratio = 1
      this.projectList = data;
    },

  },
  mounted(){
      this.loadData()
      this.loadMaterial()
  }
  
}
</script>

<style scoped lang='less'>
.nodata{
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
}
.font_red{
  color: red;
}
.font_blod{
  font-weight: bold;
}
.font_mr{
  display: inline-block;
  margin:0 10px ;
}
.searchWrap{
  padding: 15px 60px;
  display: flex;
  justify-content: center;
  align-items: center;
  .searchInput{
    height: 40px;
    flex: 1;
    border:gainsboro 1px solid;
    border-radius:  3px 0 0 3px;
    padding: 15px 5px;
  }
  .searchText{
    width: 60px;
    height: 40px;
    line-height: 40px;
    border:#26a2ff 1px solid;
    border-radius: 0 3px 3px 0;
    font-size: 14px;
    color: white;
    background: #26a2ff;
  }
}
.titleWrap{
  display: flex;
  align-items: center;
  height: 60px;
  padding: 0 30px;
  .title{
    text-align: left;
    width: 200px;
    font-size: 16px;
    font-weight: bold;
  }
  .titleRight{
    flex: 1;
    font-size: 14px;
    .count{
      flex: 1;
      display: flex;
      justify-content: flex-end;
      align-items: center;
    }
  }
  .add{
    width: 100px;  
    display: flex;
    justify-content: flex-end;
    align-items: center;
    color: orange;
    img{
        margin: 0 5px;
        width: 16px;
        height: 16px;
    }
  }
  
}
.listWrap{
    margin: 0 30px;
    margin-bottom: 60px;
    .list{
        border-bottom:#ccc 1px solid ;
        display: flex;
        align-items: center;
        justify-content: center;
        height: 40px;
        padding: 1px;
        position: relative;
        img{
          width: 24px;
          height: 24px;
          position: absolute;
          left: 20px;
        }
        li{
            height: 40px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .li_div{
            flex: 1;
            img{
              width: 20px;
              height: 20px;
            }
        }
        .right_border{
            border-right:#ccc 1px solid ;
        }
        .total,.subtotal{
            width: 150px;
        }
    }
    .operate{
        height: 40px;
        line-height: 40px;
        padding: 0 30px;
        text-align: left;
        .edit{
          color: #26a2ff;
        }
        .delete{
          color: red;
        }
        .toHistory{
          color: orange;
        }
    }
}
.pageWrap{
      padding: 30px;
      height: 40px;
      display: flex;
      align-items: center;
      justify-content: center;
  }
  .addModel{
    .modelTitle{
      text-align: center;
    }
    .modelContent{
      margin: 0 auto;
      .addName{
        padding: 0 5px;
        height: 40px;
        display: flex;
        align-items: center;
        justify-content: flex-start;
        .item5{
          margin-left: 30px;
          flex: 1;
        }
      }
      .type_content{
        width: 45%;
        margin: 0 auto;
        display: flex;
        align-items: center;
        justify-content: flex-start;
        margin-bottom: 20px;
        .input_name{
          width: 70px;
          padding: 0 5px;
          height: 40px;
          display: flex;
          align-items: center;
          justify-content: flex-start;
          margin: 0 20px;
        }
        .model_input{
          flex: 1;
        }
      }
    }
    .modelBtnWraP{
      display: flex;
      align-items: center;
      justify-content: center;
      height: 60px;
      span{
        display: flex;
        align-items: center;
        justify-content: center;
        width: 90px;
        margin: 0 30px;
        height: 30px;
        border-radius: 4px;
        border: #26a2ff 1px solid;
        cursor: pointer;
        &.confirm{
          background: #26a2ff;
          color: white;
        }
      }
    }
  }

</style>
