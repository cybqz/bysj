<template>
  <div>
    <!--title显示信息  -->
    <div class="titleWrap">
      <div class="title">
        历史报价方案列表：
      </div>
      <div class="titleRight">
        <div class="count">
          共有信息 <span class="font_red font_blod font_mr">{{total}}</span>条
        </div>
      </div>
      <div class="add">
        <Select @on-change='changeStatus' v-model="auditStatus" size="small" style="width:100px">
            <Option v-for="item in auditStatusList" :value="item.value" :key="item.value">{{ item.label }}</Option>
        </Select>
      </div>
    </div>
    <!-- list内容 -->
    <div class="listWrap ">
      <ul class="list shadow">
        <li class="listTitle listLi">
          <div class="leftText">项目编号</div>
          <div class="li_div">项目名称</div>
          <div class="li_div">是否审核</div>
          <div class="operate">操作</div>
        </li>
        <li  v-if='total<=0' class="nodata shadow">
              暂无数据
        </li>
        <li v-else v-for="(item,i) in dataList" :key="i" class="listLi listContent">
          <div class="leftText">{{item.projectId}}</div>
          <div class="li_div">{{item.projectName}}</div>
          <div class="li_div">{{item.auditStatus == 1?'是':'否'}}</div>
          <div class="operate">
            <span class="edit putHover font_mr" @click="auditStatusQuote(item.id)">审核通过</span>
            <span @click="todetail(item.id,item.templateId)" class="history putHover font_mr">查看详情</span>
            <span class="delete putHover font_mr" @click="deleteQuote(item.id)">删除</span>
          </div>
        </li>
      </ul>
      <div class="pageWrap">
          <Page :total="total" :current="pageNo" :page-size='pageSize' @on-change = 'changePage' prev-text="上一页" next-text="下一页" />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'enquiry',
  data () {
    return {
      total:0,
      pageSize:5,
      pageNo:1,
      auditStatus:'',
      auditStatusList:[
          {
              label:'全部',
              value:''
          },{
              label:'已审核',
              value:'1'
          },{
              label:'未审核',
              value:'0'
          }
      ]
    }
  },
  components:{
  },
  methods:{
    //查看详情
    todetail(id,templateId){
      this.$router.push({
        path: '/quoteDetail',
        //路由传参
        query: {
          id:id,
          templateId:templateId
        }
      })
    },
    loadData(){
      let $this = this
      this.$axios({
          method:'get', 
          url:'quote/page',
          params:{    //这里是发送给后台的数据
              pageIndex:this.pageNo,
              limit:this.pageSize,
              auditStatus:this.auditStatus
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
    auditStatusQuote(id){
      let $this = this
      this.$axios({
        method:'post',
        url:'quote/audit',
        data:$this.qs.stringify({    //这里是发送给后台的数据
            id:id
        })
      }).then((response) =>{
        if(response.data.code =='200'){
          this.$Message.success(response.data.msg);
          this.loadData()
          this.auditStatus = ''
        }else{
          this.$Message.error(response.data.msg);
        }
      })
    },
      //切换审核状态
    changeStatus(){
     this.loadData()

    },
    changePage(e){
      this.pageNo = e
      this.loadData()
    },
    deleteQuote(id){
      let $this = this
      this.$axios({
        method:'post',
        url:'quote/deleteById',
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
    width: 120px;  
    margin-left: 20px ;
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
        width: 250px;
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
      background: #989cd5;
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
