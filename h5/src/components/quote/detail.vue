<template>
  <div>
    <!--title显示信息  -->
    <div class="titleWrap">
      <div class="title">
        报价清单：
      </div>
      <div class="add putHover" @click="toQuote">
        用此模板去报价<img src="@/assets/images/add.png" alt="">
      </div>
    </div>
    <!-- list内容 -->
    <div class="listWrap shadow">
        <ul class="list">
            <li class="li_div font_blod">{{projectName}}项目报价清单</li>
            <li @click="edit()" style="color:blue;width:100px" class="putHover">编辑系数</li>
        </ul>
        <ul class="list">
            <li v-for="(objs,index) in model" :key="index" class="li_div right_border">{{objs.name}}</li>
            <li class="li_div right_border">报价小计</li>
            <li class="li_div right_border">系数</li>
            <li class="li_div right_border">实际小计</li>

        </ul>
        <ul class="list" v-for="(item ,index) in dataList" :key="index" >
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
            
        </ul>
        <ul class="list">
            <li class="li_div right_border">报价合计</li>
            <li class="total right_border">{{totalOld}}</li>
            <li class="li_div right_border">实际合计</li>
            <li class="total ">{{totalNew}}</li>
        </ul>
    </div>
    
    <!-- 图表区 -->
    <div class="echartsWrap">
      <div class="charts shadow">
        <div id="myChart1" :style="{width: '300px', height: '300px'}"></div>
      </div>
      <div class="charts shadow">
        <div id="myChart2" :style="{width: '300px', height: '300px'}"></div>
      </div>
    </div>

    <!-- 弹框区 -->
    <div class="modelWrap">
      <Modal class="addModel" :closable="false" :mask-closable="false" v-model="editModel" width="1100">
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
                <li v-for="(objs,index) in model" :key="index" class="li_div right_border">{{objs.name}}</li>
                <li class="li_div right_border">报价小计</li>
                <li class="li_div right_border">系数</li>
                <li class="li_div right_border">实际小计</li>

            </ul>
            <ul class="list" v-for="(item,i) in dataList" :key="i">
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
                <Input  placeholder="请输入系数" @on-focus='focus(i,item.ratio)' @on-blur='blur(i,item.ratio)' class="addName_input"  v-model="item.ratio" type="number" />
              </li>
              <li v-if="item" class="li_div right_border">{{item.factNum}}</li>
              
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
            <span class="cancel" @click="editCancel">取消</span>
            <span class="confirm" @click="editConfirm">确认</span>
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
              <Input class="model_input" type="text" placeholder="请输入项目名称" :maxlength="9"  v-model="quoteProjectName" />
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
  name: 'quoteDetail',
  data () {
    return {
      id:this.$route.query.id,
      templateId:this.$route.query.templateId,
      model:[],
      dataList:[],
      totalOld:0,
      totalNew:0,
      ratio:0,
      indecFlag:0,
      changgeStatus:false,
      projectName:'',
      editModel:false,
      isShowNew:false,
      QuoteModel:false,
      quoteProjectName:'',
      qouteList:[],
      qouteId:'',
      projectList:{},
      isShowNew:false,
      totalOld:0,
      totalNew:0,
      materialList:[],
      supplierList:[],
      qouteModelList:[],//最终的

    }
  },
  components:{
  },
  methods:{
    focus(i,ratio){
      this.changgeStatus = true
      this.indecFlag = i
      this.ratio = ratio
    },
    blur(i,ratio){
      this.dataList[i].ratio = ratio
      this.dataList[i].quoteNum = this.dataList[i].count*this.dataList[i].price
      this.dataList[i].factNum = this.dataList[i].count*this.dataList[i].price*ratio
      let oldNum  = 0;
      let newNum = 0
      for(let j in this.dataList){
        oldNum = oldNum + Number(this.dataList[j].qouteNum)
        newNum = newNum + Number(this.dataList[j].factNum)
      }
      this.totalOld = oldNum
      this.totalNew = newNum
    },
    //新增报价模板
    edit(){
      this.editModel = true;
    },
    //取消新增模板
    editCancel(){
      if(this.changgeStatus){
        this.dataList[this.indecFlag].ratio = this.ratio
      }
      this.editModel = false;
      this.changgeStatus =false
    },
    //确认新增模板
    editConfirm(){
      let $this = this
      this.$axios({
        method:'post',
        url:'quote/update',
        data:$this.qs.stringify({    //这里是发送给后台的数据
            id:this.id,
            projectName:this.projectName,
            totalOld : this.totalOld,
            totalNew : this.totalNew,
            contains:JSON.stringify(this.dataList) 

        })
      }).then((response) =>{
        if(response.data.code =='200'){
          this.$Message.success(response.data.msg);
          this.loadData()
          this.editModel = false;
          this.ratio = 0
          this.changgeStatus =false
        }else{
          this.$Message.error(response.data.msg);
        }
      })
      

    },
    getModel(){
      let $this = this
      this.$axios({
          method:'get', 
          url:'template/detail',
          params:{    //这里是发送给后台的数据
              id:this.templateId,
          }
      }).then((response) =>{
        if(response.data.code =='200'){
          this.model = JSON.parse(response.data.data.contains)
        }else{
          this.$Message.error(response.data.msg);
        }
      })
    },
    loadData(){
      let $this = this
      this.$axios({
          method:'get', 
          url:'quote/detail',
          params:{    //这里是发送给后台的数据
              id:this.id
          }
      }).then((response) =>{
        if(response.data.code =='200'){
          this.dataList = JSON.parse(response.data.data.contains)
          this.totalOld =response.data.data.totalOld
          this.totalNew =response.data.data.totalNew
          this.projectName = response.data.data.projectName
          let nameList = []
          let count = []
          let price = []
          for(let i in this.dataList){
            nameList.push(this.dataList[i].meterial)
            count.push(this.dataList[i].count)
            price.push(this.dataList[i].price)
          }
          
          this.loadEcharts1(nameList,count)
          this.loadEcharts2(nameList,price)
        }else{
          this.$Message.error(response.data.msg);
        }
      })
    },
    loadEcharts1(nameList,count){
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('myChart1'))
      // 绘制图表
      myChart.setOption({
          title: { text: '项目材料数量统计图' },
          tooltip: {},
          xAxis: {
              data: nameList
          },
          yAxis: {},
          series: [{
              name: '数量',
              type: 'bar',
              data: count
          }]
      });
    },
    loadEcharts2(nameList,price){
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('myChart2'))
      // 绘制图表
      let data = []
      let num = nameList.length
      for(let i = 0;i<num;i++){
          let obj = {}
          obj.value = price[i]
          obj.name = nameList[i]
          data.push(obj)
      }
      console.log(data)
      myChart.setOption({
            title: {
                text: '项目各材料报价比例',
                left: 'right'
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: nameList
            },
            series: [
                {
                    name: '报价占比',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '60%'],
                    data: data,
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
      });
    },
    toQuote(){
      this.qouteModelList=[]
      this.quoteProjectName = ''
      this.projectList = {}
      this.QuoteModel = true
      this.isShowNew = true
      this.qouteId  = this.id
      this.qouteList = this.model;
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
      debugger
      for(var key in this.projectList){
        if(!this.projectList[key]){
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
      if(!this.quoteProjectName){
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
            templateId:this.templateId,
            projectName:this.quoteProjectName,
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
      this.getModel()
      this.loadMaterial()
  }
  
}
</script>

<style scoped lang='less'>
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
  .add{
     flex: 1;  
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
        li{
            height: 40px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .li_div{
            flex: 1;
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
.echartsWrap{
    display: flex;
    .charts{
      flex: 1;
      display: flex;
      align-items: center;
      justify-content: center;
      margin: 30px;
      padding: 20px;
    }
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
