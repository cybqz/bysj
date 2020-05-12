<template>
  <div>
    
    <!-- 头部搜索 -->
    <div class="searchWrap">
      <input class="searchInput" v-model="message" placeholder="🔎搜索物料名称" type="text">
      <div class="searchText putHover" @click="toSearch">搜索</div>
    </div>

    <!-- 类型选择区 -->
    <div class="typeWrap shadow">
      <div class="typeName">类型：</div>
      <div class="typeContent">
        <span v-for="(item,i) in typeList" :key="i">{{item.name}}</span>
        <span class="add_type" @click="showTypeModel">+</span>
      </div>
    </div>
    
    <!--title显示信息  -->
    <div class="titleWrap">
      <div class="title">
        物料信息列表：
      </div>
      <div class="titleRight">
        <div class="count">
          共有信息 <span class="font_red font_blod font_mr">{{total}}</span>条
        </div>
      </div>
      <div class="add putHover" @click="showAddMaterial">
        新增信息<img src="@/assets/images/add.png" alt="">
      </div>
    </div>
    <!-- list内容 -->
    <div class="listWrap ">
      <ul class="list shadow">
        <li class="listTitle listLi">
          <div class="li_div">物料编号</div>
          <div class="li_div">物料名称</div>
          <div class="li_div">供应商</div>
          <div class="li_div">类型</div>
          <div class="li_div">标准</div>
          <div class="li_div">是否停用</div>
          <div class="operate">操作</div>
        </li>
        <li  v-if='total<=0' class="nodata">
          暂无数据
        </li>
        <li v-else class="listLi listContent" v-for="(item,i) in dataList" :key="i">
          <div class="li_div">{{item.materialId}}</div>
          <div class="li_div">{{item.materialName}}</div>
          <div class="li_div">{{item.supplierName}}</div>
          <div class="li_div">{{item.typeName}}</div>
          <div class="li_div">{{item.standard}}</div>
          <div class="li_div">{{item.isStop == '1'?"是":"否"}}</div>
          <div class="operate">
            <span class="edit putHover font_mr" 
            @click="showEditMaterial(item.id,item.materialName,item.materialId,item.supplierId,item.type,item.standard,item.isStop)">
              修改
            </span>
            <span class="delete putHover font_mr" @click="deleteMaterial(item.id)">删除</span>
          </div>
        </li>
      </ul>
      <!-- 分页区 -->
      <div class="pageWrap">
          <Page :total="total" :current="pageNo" :page-size='pageSize' @on-change = 'changePage' prev-text="上一页" next-text="下一页" />
      </div>
      <!-- model弹框区 -->
      <div>
        <Modal class="modelWrap" :closable="false" :mask-closable="false" v-model="addTypeModel" width="360">
          <p class="modelTitle" slot="header">
              添加类型
          </p>
          <div class="modelContent">
            <div class="type_content">
              <span class="input_name">类型名称：</span>
              <Input class="model_input" type="text" placeholder="请输入类型名称，最多七个字"  :maxlength=7 v-model="typeAddName" />
            </div>
          </div>
          <div slot="footer">
            <div class="modelBtnWraP">
              <span class="cancel" @click="typeCancel">取消</span>
              <span class="confirm" @click="typeConfirm">确认</span>
            </div>
          </div>
        </Modal>

        <Modal class="modelWrap" :closable="false" :mask-closable="false" v-model="addMaterialModel" width="360">
          <p class="modelTitle" slot="header">新增物料</p>
          <div class="modelContent">
            <div class="type_content">
              <span class="input_name">物料名称：</span>
              <Input class="model_input" type="text" placeholder="请输入物料名称"   v-model="materialName" />
            </div>
            <div class="type_content">
              <span class="input_name">物料标准：</span>
              <Input class="model_input" type="text" placeholder="请输入物料标准"   v-model="standard" />
            </div>
            <div class="type_content">
              <span class="input_name">供应商：</span>
              <Select  placeholder="请选择供应商"  v-model="supplierId" size="small" style="width:120px">
                <Option v-for="item in supplierList" :value="item.supplierId" :key="item.supplierId">{{ item.supplierName }}</Option>
              </Select>
            </div>
            <div class="type_content">
              <span class="input_name">物料类型：</span>
              <Select  placeholder="请选择物料类型"  v-model="typeId" size="small" style="width:120px">
                <Option v-for="item in typeList" :value="item.id" :key="item.id">{{ item.name }}</Option>
              </Select>
            </div>
            <div class="type_content">
              <span class="input_name">是否停用：</span>
              <RadioGroup v-model="isStop">
                <Radio label="1">已停用</Radio>
                <Radio label="0">未停用</Radio>
              </RadioGroup>
            </div>
          </div>
          <div slot="footer">
            <div class="modelBtnWraP">
              <span class="cancel" @click="addCancel">取消</span>
              <span class="confirm" @click="addConfirm">确认</span>
            </div>
          </div>
        </Modal>

        <Modal class="modelWrap" :closable="false" :mask-closable="false" v-model="editMaterialModel" width="360">
          <p class="modelTitle" slot="header">
              编辑物料
          </p>
          <div class="modelContent">
            <div class="type_content">
              <span class="input_name">物料名称：</span>
              <Input class="model_input" type="text" placeholder="请输入物料名称"   v-model="materialName" />
            </div>
            <div class="type_content">
              <span class="input_name">物料标准：</span>
              <Input class="model_input" type="text" placeholder="请输入物料标准"   v-model="standard" />
            </div>
            <div class="type_content">
              <span class="input_name">供应商：</span>
              <Select  placeholder="请选择供应商"  v-model="supplierId" size="small" style="width:120px">
                <Option v-for="item in supplierList" :value="item.supplierId" :key="item.supplierId">{{ item.supplierName }}</Option>
              </Select>
            </div>
            <div class="type_content">
              <span class="input_name">物料类型：</span>
              <Select  placeholder="请选择物料类型"  v-model="typeId" size="small" style="width:120px">
                <Option v-for="item in typeList" :value="item.id" :key="item.id">{{ item.name }}</Option>
              </Select>
            </div>
            <div class="type_content">
              <span class="input_name">是否停用：</span>
              <RadioGroup v-model="isStop">
                <Radio label="1">已停用</Radio>
                <Radio label="0">未停用</Radio>
              </RadioGroup>
            </div>
          </div>
          <div slot="footer">
            <div class="modelBtnWraP">
              <span class="cancel" @click="editCancel">取消</span>
              <span class="confirm" @click="editConfirm">确认</span>
            </div>
          </div>
        </Modal>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'material',
  data () {
    return {
      message:'',
      dataList:[],
      typeList:[],
      supplierList:[],
      total:0,
      pageNo:1,
      pageSize:5,
      //关于类型的
      addTypeModel:false,
      typeAddName:'',
      //关于物料的
      addMaterialModel:false,
      materialName:'',
      materialId:'',
      standard:'',
      isStop:'0',
      id : '',
      supplierId : '',
      typeId : '',
      editMaterialModel:false,
    }
  },
  components:{
  },
  methods:{
    toSearch(){
      this.pageNo = 1
      this.loadData()
    },
    loadType(){
      let $this = this
      this.$axios({
          method:'get', url:'parames/getMaterialType',
      }).then((response) =>{
        if(response.data.code =='200'){
          this.typeList = response.data.data
        }else{
          this.$Message.error(response.data.msg);
        }
      })
    },
    loadSupplier(){
      let $this = this
      this.$axios({
          method:'get', 
          url:'supplier/list',
         
      }).then((response) =>{
        if(response.data.code =='200'){
          this.supplierList = response.data.data
        }else{
          this.$Message.error(response.data.msg);
        }
      })
    },
    loadData(){
      let $this = this
      this.$axios({
          method:'get',
          url:'material/page',
          params:{    //这里是发送给后台的数据
              materialName:this.message,
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
    showAddMaterial(){
      this.materialName = ''
      this.standard = ''
      this.supplierId = ''
      this.typeId = ''
      this.isStop = '0'
      this.addMaterialModel = true;
    },
    addCancel(){
      this.materialName = ''
      this.standard = ''
      this.supplierId = ''
      this.typeId = ''
      this.isStop = '0'
      this.addMaterialModel = false;

    },
    addConfirm(){
      if(!this.materialName){
        this.$Message.error(
          {
            content: '物料名称不能为空',
            duration: 2000
          }
        );
        return
      }
      if(!this.standard){
        this.$Message.error(
          {
            content: '物料标准不能为空',
            duration: 2000
          }
        );
        return
      }
      if(!this.supplierId){
        this.$Message.error(
          {
            content: '供应商不能为空',
            duration: 2000
          }
        );
        return
      }
      if(!this.typeId){
        this.$Message.error(
          {
            content: '物料类型不能为空',
            duration: 2000
          }
        );
        return
      }
      let $this = this
      this.$axios({
        method:'post',
        url:'material/save',
        data:$this.qs.stringify({    //这里是发送给后台的数据
            supplierId:this.supplierId,
            materialName:this.materialName,
            type:this.typeId,
            isStop:this.isStop,
            standard:this.standard,
        })
      }).then((response) =>{
        if(response.data.code =='200'){
          this.loadData()
          this.addMaterialModel = false;
          this.materialName = ''
          this.standard = ''
          this.supplierId = ''
          this.typeId = ''
          this.isStop = '0'
        }else{
          this.$Message.error(response.data.msg);
        }
      })
      
    },
    showEditMaterial(id,materialName,materialId,supplierId,typeId,standard,isStop){
      this.materialName = materialName
      this.materialId = materialId
      this.standard = standard
      this.isStop = isStop
      this.id = id
      this.supplierId = supplierId
      this.typeId = typeId
      this.editMaterialModel = true;
    },
    editCancel(){
      this.materialName = ''
      this.standard = ''
      this.isStop = '0'
      this.id = ''
      this.supplierId = ''
      this.materialId = ''
      this.typeId = ''
      this.editMaterialModel = false;

    },
    editConfirm(){
      if(!this.materialName){
        this.$Message.error(
          {
            content: '物料名称不能为空',
            duration: 2000
          }
        );
        return
      }
      if(!this.standard){
        this.$Message.error(
          {
            content: '物料标准不能为空',
            duration: 2000
          }
        );
        return
      }
      if(!this.supplierId){
        this.$Message.error(
          {
            content: '供应商不能为空',
            duration: 2000
          }
        );
        return
      }
      if(!this.typeId){
        this.$Message.error(
          {
            content: '物料类型不能为空',
            duration: 2000
          }
        );
        return
      }
      let $this = this
      this.$axios({
        method:'post',
        url:'material/update',
        data:$this.qs.stringify({    //这里是发送给后台的数据
            supplierId:this.supplierId,
            materialName:this.materialName,
            type:this.typeId,
            isStop:this.isStop,
            standard:this.standard,
            id:this.id
        })
      }).then((response) =>{
        if(response.data.code =='200'){
          this.loadData()
          this.editMaterialModel = false;
          this.materialName = ''
          this.standard = ''
          this.isStop = '0'
          this.id = ''
          this.supplierId = ''
          this.typeId = ''
          this.materialId = ''
        }else{
          this.$Message.error(response.data.msg);
        }
      })
    },
    deleteMaterial(id){
      let $this = this
      this.$axios({
        method:'post',
        url:'material/deleteById',
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
    showTypeModel(){
      this.typeAddName = ''
      this.addTypeModel = true
    },
    typeCancel(){
      this.addTypeModel = false;
      this.typeAddName = ''

    },
    typeConfirm(){
      if(!this.typeAddName){
        this.$Message.error(
          {
            content: '物料类型不能为空',
            duration: 2000
          }
        );
        return
      }
      let $this = this
      this.$axios({
        method:'post',
        url:'parames/saveMaterialType',
        data:$this.qs.stringify({    //这里是发送给后台的数据
            name:this.typeAddName,
        })
      }).then((response) =>{
        if(response.data.code =='200'){
          this.loadType()
          this.addTypeModel = false;
          this.typeAddName = ''
        }else{
          this.$Message.error(response.data.msg);
        }
      })
      
    },
    changePage(e){
      this.pageNo = e;
      this.loadData();
    }
  },
  mounted(){
    this.loadData();
    this.loadType();
    this.loadSupplier();
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
  margin:0 3px ;
}
.typeWrap{
  padding:0 15px;
  margin: 30px;
  color: orange;
  .typeName{
    height: 40px;
    line-height: 40px;
    font-size: 16px;
    text-align: left;
  }
  .typeContent{
    display: flex;
    align-items: center;
    flex-direction: row;
    flex-wrap: wrap;
    span{
      height: 20px;
      display: flex;
      align-items: center;
      border-radius: 3px;
      border:1px solid orange;
      font-size: 14px;
      margin: 15px;
      padding: 4px 10px;
      &.add_type{
        border:1px solid #26a2ff;
        color: #26a2ff;
        cursor: pointer;
      }
    }
  }
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
  margin: 30px;
  .list{
    .listLi{
      padding: 5px;
      height:50px ;
      display: flex;
      align-items: center;
      font-size: 16px;
      border-bottom: #909090 1px solid;
      &:last-child{
        border-bottom: none;
      }
      .li_div{
        flex: 1;
        padding: 0 3px;
      }
      .leftText{
        width: 200px; 
        text-align: left;
        padding: 0 3px;
      }
      
      .operate{
        width: 150px;
        padding: 0 3px;
        .edit{
          font-size: 14px;
          color: #26a2ff;
        }
        .delete{
          font-size: 14px;
          color: red;
        }
      }

    }
    .listTitle{
      background: #ddae9c;
      color: white;
      border-radius: 4px 4px 0 0;
    }
    .listContent{
      font-size: 14px;
    }
    
  }
  .pageWrap{
      padding: 30px;
      height: 40px;
  }
}

.modelWrap{
  .modelTitle{
      text-align: center;
    }
    .modelContent{
      .type_content{
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
