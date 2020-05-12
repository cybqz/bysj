<template>
  <div>
    
    <!--title显示信息  -->
    <div class="titleWrap">
      <div class="title">
        历史询价信息：
      </div>
      <div class="titleRight">
        <div class="count">
          共有信息 <span class="font_red font_blod font_mr">{{total}}</span>条
        </div>
      </div>
    </div>
    <!-- list内容 -->
    <div class="listWrap ">
      <ul class="list shadow">
        <li class="listTitle listLi">
          <div class="leftText">物料名称</div>
          <div class="leftText">物料编号</div>
          <div class="li_div">供应商</div>
          <div class="li_div">价格</div>
          <div class="li_div">计量单位</div>
          <div class="li_div">时间</div>
        </li>

        <li v-for="(item,i) in dataList" :key="i" class="listLi listContent">
          <div class="leftText">{{item.materialId}}</div>
          <div class="leftText">{{item.materialName}}</div>
          <div class="li_div">{{item.supplierName}}</div>
          <div class="li_div">{{item.price}}</div>
          <div class="li_div">{{item.unit}}</div>
          <div class="li_div">{{item.createDateTime}}</div>
        </li>
      </ul>
     
    </div>

    <!-- 分页区 -->
    <div class="pageWrap">
        <Page :total="total" :current="pageNo" :page-size='pageSize' @on-change = 'changePage' prev-text="上一页" next-text="下一页" />
    </div>
  </div>
</template>

<script>
export default {
  name: 'enquiryHistory',
  data () {
    return {
      total:0,
      pageNo:1,
      pageSize:10,
      dataList:[],
      belongId:this.$route.query.belongId || ''
    }
  },
  components:{
  },
  methods:{
    loadData(){
      let $this = this
      this.$axios({
        method:'get',
        url:'enquiry/historypage',
        params:{    //这里是发送给后台的数据
            belongId:this.belongId,
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
    changePage(e){
      this.pageNo = e;
      this.loadData();
    },
  },
  mounted(){
    this.loadData()
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
  margin:0 3px ;
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
</style>
