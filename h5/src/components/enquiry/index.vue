<template>
  <div>
    <!-- 头部搜索 -->
    <div class="searchWrap">
      <input class="searchInput" v-model="message" placeholder="🔎搜索物料名称" type="text">
      <div class="searchText putHover" @click="toSearch">搜索</div>
    </div>
    <!--title显示信息  -->
    <div class="titleWrap">
      <div class="title">
        询价信息列表：
      </div>
      <div class="titleRight">
        <div class="count">
          共有信息 <span class="font_red font_blod font_mr">30</span>条
        </div>
      </div>
      <div @click="showAddmodel" class="add putHover">
        新增信息<img src="@/assets/images/add.png" alt="">
      </div>
    </div>
    <!-- list内容 -->
    <div class="listWrap ">
      <ul class="list shadow">
        <li class="listTitle listLi">
          <div class="leftText">物料编号</div>
          <div class="leftText">物料名称</div>
          <div class="li_div">供应商</div>
          <div class="li_div">价格</div>
          <div class="li_div">计量单位</div>
          <div class="operate">操作</div>
        </li>
        <li  v-if='total<=0' class="nodata">
          暂无数据
        </li>
        <li v-else v-for="(item,i) in dataList" :key="i" class="listLi listContent">
          <div class="leftText">{{item.materialId}}</div>
          <div class="leftText">{{item.materialName}}</div>
          <div class="li_div">{{item.supplierName}}</div>
          <div class="li_div">{{item.price}}</div>
          <div class="li_div">{{item.unit}}</div>
          <div class="operate">
            <span
            @click="showEditModel(item.id,item.materialId,item.materialName,item.supplierId,item.supplierName,item.price,item.unit,item.belongId)"
             class="edit putHover font_mr">修改</span>
            <span @click="toHistory(item.belongId)" class="history putHover font_mr">历史定价</span>
            <span @click="deleteEnquiry(item.id)" class="delete putHover font_mr">删除</span>
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
          <p class="modelTitle" slot="header">新增询价信息</p>
          <div class="modelContent">
            <div class="type_content">
              <span class="input_name">物料名称：</span>
              <Select  placeholder="请选择物料名称"  @on-change='loadSupplier(materialId)'  v-model="materialId" size="small" style="width:120px">
                <Option v-for="item in materialList" :value="item.materialId" :key="item.materialId">{{ item.materialName }}</Option>
              </Select>
            </div>
            <div class="type_content">
              <span class="input_name">供应商：</span>
              <Select  placeholder="请选择供应商" v-model="supplierId" size="small" style="width:120px">
                <Option v-for="item in supplierList" :value="item.supplierId" :key="item.supplierId">{{ item.supplierName }}</Option>
              </Select>
            </div>
            <div class="type_content">
              <span class="input_name">价格：</span>
              <Input class="model_input" type="number" placeholder="请输入价格"   v-model="price" />
            </div>
            <div class="type_content">
              <span class="input_name">计量单位：</span>
              <Input class="model_input" type="text" placeholder="请输入计量单位"   v-model="unit" />
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
          <p class="modelTitle" slot="header">新增询价信息</p>
          <div class="modelContent">
            <div class="type_content">
              <span class="input_name">物料名称：</span>
              <Select  placeholder="请选择物料名称" @on-change='loadSupplier(materialId)' v-model="materialId" size="small" style="width:120px">
                <Option v-for="item in materialList"  :value="item.materialId" :key="item.materialId">{{ item.materialName }}</Option>
              </Select>
            </div>
            <div class="type_content">
              <span class="input_name">供应商：</span>
              <Select  placeholder="请选择供应商"   v-model="supplierId" size="small" style="width:120px">
                <Option v-for="item in supplierList" :value="item.supplierId" :key="item.supplierId">{{ item.supplierName }}</Option>
              </Select>
            </div>
            <div class="type_content">
              <span class="input_name">价格：</span>
              <Input class="model_input" type="number" placeholder="请输入价格"   v-model="price" />
            </div>
            <div class="type_content">
              <span class="input_name">计量单位：</span>
              <Input class="model_input" type="text" placeholder="请输入计量单位"   v-model="unit" />
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
  name: 'enquiry',
  data () {
    return {
      message:'',
      total:0,
      pageNo:1,
      pageSize:10,
      dataList:[],
      supplierList:[],
      materialList:[],
      addModel:false,
      id:'',
      materialId:'',
      materialName:'',
      supplierId:'',
      supplierName:'',
      price:'',
      unit:'',
      belongId:'',
      editModel:false,
    }
  },
  components:{
  },
  methods:{
    toSearch(){
      this.pageNo = 1
      this.loadData()
    },
    loadData(){
      let $this = this
      this.$axios({
          method:'get',
          url:'enquiry/page',
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
    openSupplier(){
      if(!this.materialId){
         this.$Message.error('请先选择物料名称！');
      }else{
        this.loadSupplier(this.materialId)
      }
    },
    loadSupplier(id){
      let name = ''
      for( let i in this.materialList){
        if(id == this.materialList[i].materialId){
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

    showAddmodel(){
      this.addModel = true
      this.id = ''
      this.materialId = ''
      this.materialName = ''
      this.supplierId = ''
      this.supplierName = ''
      this.price = ''
      this.unit = ''
    },
    addCancel(){
      this.addModel = false
      this.id = ''
      this.materialId = ''
      this.materialName = ''
      this.supplierId = ''
      this.supplierName = ''
      this.price = ''
      this.unit = ''
    },
    addConfirm(){
      if(!this.materialId){
        this.$Message.error(
          {
            content: '物料名称不能为空',
            duration: 2000
          }
        );
        return
      }
      if(!this.price){
        this.$Message.error(
          {
            content: '价格不能为空',
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
        );unit
        return
      }
      if(!this.unit){
        this.$Message.error(
          {
            content: '计量单位不能为空',
            duration: 2000
          }
        );
        return
      }
      let $this = this
      this.$axios({
        method:'post',
        url:'enquiry/save',
        data:$this.qs.stringify({    //这里是发送给后台的数据
            supplierId:this.supplierId,
            materialId:this.materialId,
            price:this.price,
            unit:this.unit,
        })
      }).then((response) =>{
        if(response.data.code =='200'){
          this.loadData()
          this.addModel = false
          this.id = ''
          this.materialId = ''
          this.materialName = ''
          this.supplierId = ''
          this.supplierName = ''
          this.price = ''
          this.unit = ''
        }else{
          this.$Message.error(response.data.msg);
        }
      })

    },
    showEditModel(id,materialId,materialName,supplierId,supplierName,price,unit,belongId){
      this.editModel = true
      this.id = id
      this.materialId = materialId
      this.materialName = materialName
      this.supplierId = supplierId
      this.supplierName = supplierName
      this.price = price
      this.unit = unit
      this.belongId = belongId
    },
    editCancel(){
      this.editModel = false
      this.id = ''
      this.belongId = ''
      this.materialId = ''
      this.materialName = ''
      this.supplierId = ''
      this.supplierName = ''
      this.price = ''
      this.unit = ''

    },
    editConfirm(){
      if(!this.materialId){
        this.$Message.error(
          {
            content: '物料名称不能为空',
            duration: 2000
          }
        );
        return
      }
      if(!this.price){
        this.$Message.error(
          {
            content: '价格不能为空',
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
        );unit
        return
      }
      if(!this.unit){
        this.$Message.error(
          {
            content: '计量单位不能为空',
            duration: 2000
          }
        );
        return
      }
      let $this = this
      this.$axios({
        method:'post',
        url:'enquiry/update',
        data:$this.qs.stringify({    //这里是发送给后台的数据
            id:this.id,
            belongId:this.belongId,
            supplierId:this.supplierId,
            materialId:this.materialId,
            price:this.price,
            unit:this.unit,
        })
      }).then((response) =>{
        if(response.data.code =='200'){
          this.loadData()
          this.editModel = false
          this.id = ''
          this.belongId = ''
          this.materialId = ''
          this.materialName = ''
          this.supplierId = ''
          this.supplierName = ''
          this.price = ''
          this.unit = ''
        }else{
          this.$Message.error(response.data.msg);
        }
      })

    },
    deleteEnquiry(id){
        let $this = this
      this.$axios({
        method:'post',
        url:'enquiry/deleteById',
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
    changePage(e){
      this.pageNo = e;
      this.loadData();
    },
    toHistory(belongId){
      this.$router.push({
        path: '/enquiryHistory',
        //路由传参
        query: {
          belongId:belongId
        }
      })
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
        width: 210px;
        padding: 0 3px;
        .edit{
          font-size: 14px;
          color: #26a2ff;
        }
        .delete{
          font-size: 14px;
          color: red;
        }
        .history{
          font-size: 14px;
          color: orange;
        }
      }

    }
    .listTitle{
      background: #9cdda7;
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
