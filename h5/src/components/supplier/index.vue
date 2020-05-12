<template>
  <div>
    <!-- 头部搜索 -->
    <div class="searchWrap">
      <input class="searchInput" v-model="message" placeholder="🔎请对供应商名称或编号或产地进行搜索" type="text">
      <div class="searchText putHover" @click="toSearchName(1)">搜索名称</div>
      <div class="searchText putHover" @click="toSearchID(1)">搜索编号</div>
      <div class="searchText putHover" @click="toSearchLocal(1)">搜索产地</div>
    </div>
    <!--title显示信息  -->
    <div class="titleWrap">
      <div class="title">
        供应商信息列表：
      </div>
      <div class="titleRight">
        
        <div class="count">
          共有信息 <span class="font_red font_blod font_mr">{{total}}</span>条
        </div>
      </div>
      <div @click="showAddModel()" class="add">
        新增信息<img src="@/assets/images/add.png" alt="">
      </div>
    </div>
    <!-- list内容 -->
    <div class="listWrap ">
      <ul class="list shadow">
        <li class="listTitle listLi">
          <div class="li_div">供应商编号</div>
          <div class="li_div">供应商名称</div>
          <div class="li_div">供应商产地</div>
          <div class="li_div">详细地址</div>
          <div class="li_div">联系人</div>
          <div class="li_div">联系方式</div>
          <div class="operate">操作</div>
        </li>
        <li  v-if='total<=0' class="nodata">
          暂无数据
        </li>
        <li v-else class="listLi listContent" v-for="(item,i) in dataList" :key="i">
          <div class="li_div">{{item.supplierId}}</div>
          <div class="li_div">{{item.supplierName}}</div>
          <div class="li_div">{{item.originPlace}}</div>
          <div class="li_div">{{item.address}}</div>
          <div class="li_div">{{item.supplierUser}}</div>
          <div class="li_div">{{item.supplierPhone}}</div>
          <div class="operate">
            <span 
            @click="showEditModel(item.id,item.supplierId,item.supplierName,item.originPlace,item.address,item.supplierUser,item.supplierPhone)" 
            class="edit putHover font_mr">修改</span>
            <Poptip
              confirm
              title="删除该供应商，会将供应商名下的所有物料信息及询价信息都会被删除，确定删除吗？"
              @on-ok="deleteSupplier(item.id)"
              @on-cancel="deletecancel"
               placement="bottom-end">
              <span  class="delete putHover font_mr">删除</span>
            </Poptip>
            <!-- <span @click="deleteSupplier(item.id)" class="delete putHover font_mr">删除</span> -->
          </div>
        </li>
      </ul>
      <!-- 分页区 -->
      <div class="pageWrap">
          <Page :total="total" :current="pageNo" :page-size='pageSize' @on-change = 'changePage' prev-text="上一页" next-text="下一页" />
      </div>
      <!-- model潭框区 -->
      <div>
        <Modal class="modelWrap" :closable="false" :mask-closable="false" v-model="addModel" width="360">
          <p class="modelTitle" slot="header">添加供应商</p>
          <div class="modelContent">
            <div class="type_content">
              <span class="input_name">供应商名称：</span>
              <Input class="model_input" type="text" placeholder="请输入供应商名称" v-model="supplierName" />
            </div>
            <div class="type_content">
              <span class="input_name">供应商产地：</span>
              <Input class="model_input" type="text" placeholder="请输入供应商产地" v-model="originPlace" />
            </div>
            <div class="type_content">
              <span class="input_name">详细地址：</span>
              <Input class="model_input" type="text" placeholder="请输入详细地址" v-model="address" />
            </div>
            <div class="type_content">
              <span class="input_name">联系人：</span>
              <Input class="model_input" type="text" placeholder="请输入联系人" v-model="supplierUser" />
            </div>
            <div class="type_content">
              <span class="input_name">联系方式：</span>
              <Input class="model_input" type="tel" :maxlength=11 placeholder="请输入联系方式" v-model="supplierPhone" />
            </div>
          </div>
          <div slot="footer">
            <div class="modelBtnWraP">
              <span class="cancel" @click="addCancel">取消</span>
              <span class="confirm" @click="addConfirm">确认</span>
            </div>
          </div>
        </Modal>

        <Modal class="modelWrap" :closable="false" :mask-closable="false" v-model="editModel" width="360">
          <p class="modelTitle" slot="header">
              修改供应商
          </p>
          <div class="modelContent">
            <div class="type_content">
              <span class="input_name">供应商名称：</span>
              <Input class="model_input" type="text" placeholder="请输入供应商名称" v-model="supplierName" />
            </div>
            <div class="type_content">
              <span class="input_name">供应商产地：</span>
              <Input class="model_input" type="text" placeholder="请输入供应商产地" v-model="originPlace" />
            </div>
            <div class="type_content">
              <span class="input_name">详细地址：</span>
              <Input class="model_input" type="text" placeholder="请输入详细地址" v-model="address" />
            </div>
            <div class="type_content">
              <span class="input_name">联系人：</span>
              <Input class="model_input" type="text" placeholder="请输入联系人" v-model="supplierUser" />
            </div>
            <div class="type_content">
              <span class="input_name">联系方式：</span>
              <Input class="model_input" type="tel" :maxlength=11 placeholder="请输入联系方式" v-model="supplierPhone" />
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
  name: 'supplier',
  data () {
    return {
      message:'',
      dataList:[],
      total:0,
      pageNo:1,
      pageSize:1,
      addModel:false,
      supplierName:'',
      originPlace:'',
      address:'',
      supplierUser:'',
      supplierPhone:'',
      id:'',
      supplierId:'',
      editModel:false,
      searchType:0

    }
  },
  components:{
  },
  methods:{
    toSearchName(num){
      this.searchType= 1
      this.pageNo = num?num:1
      let $this = this
      this.$axios({
        method:'get',
        url:'supplier/page',
        params:{    //这里是发送给后台的数据
            supplierName:this.message,
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
    toSearchID(num){
      this.searchType= 2
      this.pageNo = num?num:1
      let $this = this
      this.$axios({
        method:'get',
        url:'supplier/page',
        params:{    //这里是发送给后台的数据
            supplierId:this.message,
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
    toSearchLocal(num){
      this.searchType= 3
      this.pageNo = num?num:1
      let $this = this
      this.$axios({
        method:'get',
        url:'supplier/page',
        params:{    //这里是发送给后台的数据
            originPlace:this.message,
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
    loadData(num){
      this.searchType= 0
      let $this = this
      this.pageNo = num?num:1
      this.$axios({
          method:'get', 
          url:'supplier/page',
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
    showAddModel(){
      this.addModel = true
      this.supplierName =''
      this.originPlace =''
      this.address =''
      this.supplierUser =''
      this.supplierPhone =''
       
    },
    addCancel(){
      this.addModel = false
      this.supplierName =''
      this.originPlace =''
      this.address =''
      this.supplierUser =''
      this.supplierPhone =''
    },
    addConfirm(){
      if(!this.supplierName){
        this.$Message.error(
          {
            content: '供应商名称不能为空',
            duration: 2000
          }
        );
        return
      }
      if(!this.originPlace){
        this.$Message.error(
          {
            content: '供应商产地不能为空',
            duration: 2000
          }
        );
        return
      }
      if(!this.address){
        this.$Message.error(
          {
            content: '详细地址不能为空',
            duration: 2000
          }
        );
        return
      }
      if(!this.supplierUser){
        this.$Message.error(
          {
            content: '联系人不能为空',
            duration: 2000
          }
        );
        return
      }
      if(!this.supplierPhone){
        this.$Message.error(
          {
            content: '联系电话不能为空',
            duration: 2000
          }
        );
        return
      }
      let $this = this
      this.$axios({
        method:'post',
        url:'supplier/save',
        data:$this.qs.stringify({    //这里是发送给后台的数据
            supplierName:this.supplierName,
            originPlace:this.originPlace,
            address:this.address,
            supplierUser:this.supplierUser,
            supplierPhone:this.supplierPhone,
        })
      }).then((response) =>{
        if(response.data.code =='200'){
          this.loadData()
          this.addModel = false
          this.supplierName =''
          this.originPlace =''
          this.address =''
          this.supplierUser =''
          this.supplierPhone =''
        }else{
          this.$Message.error(response.data.msg);
        }
      })
      
    },
    showEditModel(id,supplierId,supplierName,originPlace,address,supplierUser,supplierPhone){
      this.editModel = true
      this.id = id
      this.supplierId = supplierId
      this.supplierName = supplierName
      this.originPlace = originPlace
      this.address = address
      this.supplierUser = supplierUser
      this.supplierPhone = supplierPhone
    },
    editCancel(){
      this.editModel = false
      this.id = ''
      this.supplierId = ''
      this.supplierName =''
      this.originPlace =''
      this.address =''
      this.supplierUser =''
      this.supplierPhone =''
    },
    editConfirm(){
      if(!this.supplierName){
        this.$Message.error(
          {
            content: '供应商名称不能为空',
            duration: 2000
          }
        );
        return
      }
      if(!this.originPlace){
        this.$Message.error(
          {
            content: '供应商产地不能为空',
            duration: 2000
          }
        );
        return
      }
      if(!this.address){
        this.$Message.error(
          {
            content: '详细地址不能为空',
            duration: 2000
          }
        );
        return
      }
      if(!this.supplierUser){
        this.$Message.error(
          {
            content: '联系人不能为空',
            duration: 2000
          }
        );
        return
      }
      if(!this.supplierPhone){
        this.$Message.error(
          {
            content: '联系电话不能为空',
            duration: 2000
          }
        );
        return
      }
      let $this = this
      this.$axios({
        method:'post',
        url:'supplier/update',
        data:$this.qs.stringify({    //这里是发送给后台的数据
            id:this.id,
            supplierId:this.supplierId,
            supplierName:this.supplierName,
            originPlace:this.originPlace,
            address:this.address,
            supplierUser:this.supplierUser,
            supplierPhone:this.supplierPhone,
        })
      }).then((response) =>{
        if(response.data.code =='200'){
          this.loadData()
          this.editModel = false
          this.id = ''
          this.supplierId = ''
          this.supplierName =''
          this.originPlace =''
          this.address =''
          this.supplierUser =''
          this.supplierPhone =''
        }else{
          this.$Message.error(response.data.msg);
        }
      })
      
    },
    deleteSupplier(id){
      let $this = this
      this.$axios({
        method:'post',
        url:'supplier/deleteById',
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
    deletecancel(){},
    changePage(e){
      switch(this.searchType) {
        case 0:
            this.loadData(e)
            break;
        case 1:
            this.toSearchName(e)
            break;
        case 3:
            this.toSearchID(e)
            break;
        case 4:
            this.toSearchLocal(e)
            break;   
        default:
            this.loadData(e)
    } 
    }

  },
  mounted(){
    this.loadData()
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
.searchWrap{
  padding: 15px 60px;
  display: flex;
  justify-content: center;
  align-items: center;
  .searchInput{
    height: 40px;
    flex: 1;
    border:gainsboro 1px solid;
    border-radius:  4px;
    padding: 15px 5px;
  }
  .searchText{
      margin-left: 40px;
    width: 80px;
    height: 40px;
    line-height: 40px;
    border:#26a2ff 1px solid;
    border-radius: 4px;
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
      background: #a99cdd;
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
          width: 85px;
          padding: 0 5px;
          height: 40px;
          display: flex;
          align-items: center;
          justify-content: flex-start;
          margin: 0 10px;
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
.ivu-poptip-confirm .ivu-poptip-body .ivu-icon{
  position: relative;
}
</style>
