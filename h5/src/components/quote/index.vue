<template>
  <div>
      <div class="tabWrap">
          <div class="tabLeft">
              <span :class="active == 1?'active':''" class="putHover" @click=" changeTab(1)">报价模板</span>
              <span class="tab_line">|</span>
              <span :class="active == 2?'active':''" class="putHover" @click=" changeTab(2)">历史报价方案</span>
          </div>
                
          <div class="tabRight ">
            <Poptip v-model="visible" placement="bottom-end" class="add " title="" >
                去新增报价<img src="@/assets/images/goDeal.png" alt="">
                <div slot="content" >
                    <div @click=" changeTab(1)" class="addBtn putHover">去报价模板新增</div>
                    <div @click=" changeTab(2)" class="addBtn putHover">去采纳历史方案</div>
                </div>
            </Poptip>
          </div>
      </div>
      <div class="contentWrap">
          <quoteModel v-if="active == 1"></quoteModel>
          <quoteHistory v-if="active == 2"></quoteHistory>
      </div>
    
  </div>
</template>

<script>
import quoteModel from './quoteModel.vue'
import quoteHistory from './quoteHistory.vue'
export default {
  name: 'register',
  data () {
    return {
      active:1,
      visible:false,
    }
  },
  components:{
      quoteModel,quoteHistory
  },
  methods:{
    changeTab(num){
        this.active = num;
        this.visible = false;
    }
    
  },
  mounted(){
  }
  
}
</script>

<style scoped lang='less'>
.tabWrap{
    height: 60px;
    display: flex;
    align-items: center;
    font-size: 14px;
    color: orange;
    .tabLeft,.tabRight{
        flex: 1;
        display: flex;
        align-items: center;
    }
    .tabLeft{
        justify-content: flex-start;
        span{
            display: inline-block;
            padding: 10px;
            &.tab_line{
                margin: 0 30px;
            }
            &.active{
                color:#26a2ff ;
            }
        }
        
    }
    .tabRight{
        justify-content: flex-end;
        .add{
            cursor: pointer;
        }
        img{
            margin: 0 5px;
            width: 16px;
            height: 16px;
            vertical-align: text-top;
            position: relative;
            top:-1px;
        }
        .addBtn{
            height: 40px;
            line-height: 40px;
        }
    }
}
.contentWrap{
    border-top: #ccc 1px solid;
    padding: 30px 0;
}
</style>
