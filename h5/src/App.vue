<template>
  <div id="app">
    <!-- 头部导航 -->
    <div v-if="isLogined" class="topWrap">
      <div class="logo">
        工程报价管理系统
      </div>
      <div class="bar">
        <div v-for="(item,i) in barList" :key="i" 
          :class="isActive == i?'isActive' : ''" 
          @click="changeBar(i)"
          class="barName putHover">
          {{item}}
        </div>
      </div>
      <div @click="setUser()" class="quit putHover">
        <img src="@/assets/images/set.png" alt="">
        修改密码
      </div>
      <div @click="quit()" class="quit putHover">
        <img src="@/assets/images/quit.png" alt="">
        退出登录
      </div>
    </div>
    <!-- 内容区和用户卡片 -->
    <div v-if="isLogined" class="botWrap shadow">
      <router-view/>
    </div>
    <div v-if="!isLogined">
      <router-view/>
    </div>
    
    
  </div>
</template>

<script>
export default {
  name: 'App',
  data () {
    return {
      isLogined:false,
      barList:['材料信息','供应商信息','询价信息','综合报价'],
      isActive:'0',
    }
  },
  created(){
    this.$router.push({
            path: '/',
          })
    let token = sessionStorage.getItem("token");
    if(token ){
      this.isLogined = true
    }else{
      this.isLogined = false
    }
  },
  methods:{
    setUser(){
      this.$router.push({
        path: '/password',
      })
    },
    //退出登录
    quit(){
      this.$axios({
          method:'post', url:'login/logout',
      }).then((res) =>{
        sessionStorage.clear();
        window.location.reload()
      })
    },
    //切换导航
    changeBar(i){
      this.isActive  = i;
      switch (i){
				case 0:
          this.$router.push({
            path: '/',
            name: 'material'
          })
					break;
				case 1:
					this.$router.push({
            path: '/supplier',
            name: 'supplier'
					})
					break;
				case 2:
					this.$router.push({
            path: '/enquiry',
            name: 'enquiry'
            
					})
					break;
				case 3:
					this.$router.push({
            path: '/quote',
            name: 'quote'
					})
          break;	
        case 4:
					this.$router.push({
            path: '/my',
            name: 'my'
					})
					break;  	
				default:
					break;
			}
		
    }
  },
  mounted(){
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
html,body,ul,li,ol,dl,dd,dt,p,h1,h2,h3,h4,h5,h6,img {
    margin:0;
    padding:0;
}
html,body{
  font-size: 12px;
}
.shadow{
  box-shadow: inset 0 0 5px 0 gainsboro;
  border-radius: 4px;
}
ul,ol {
    list-style:none
}
.putHover:hover{
 color: #00a6ff;
  cursor: pointer;
}
.pad15{
  padding: 15px;
}
</style>
<style scoped lang='less'>
.topWrap{
  height: 60px;
  display: flex;
  align-items: center;
  padding: 0 15px;
  border-bottom: 1px solid gainsboro;
  .logo{
    text-align: left;
    width: 200px;
    img{
      width: 50px;
      vertical-align: text-bottom;
      margin-right: 5px;
    }
    font-size: 22px;
    color: #909090;
  }
  .bar{
    padding: 0 20px;
    padding-top:10px;
    flex: 1;
    display: flex;
    align-items: center;
    font-size: 16px;
    color:orange;
    .barName{
      flex: 1;
      &.isActive{
        color: #00a6ff;
        cursor: pointer;
      }
    }
  }
  .quit{
    padding-top:10px;
    margin-left: 30px;
    width: 80px;
    text-align: right;
    color:orange;
    img{
      width: 16px;
      vertical-align: text-bottom;
      margin-right: 5px;
    }
  }
}
.putHover:hover{
 color: #00a6ff;
  cursor: pointer;
}

.botWrap{
  width: 80%;
  margin: 60px auto;
  padding: 30px;
}
.appModel{
  .setWrap,&.setBtn{
    width: 100%;
    height: 30PX;
    margin: 50px auto;
    padding: 15px;
    display: flex;
    align-items: center;
    font-size: 14px;
    justify-content: center;
    .setTitle{
      display: inline-block;
      width: 80px;
      padding: 10px;
      text-align: center;
      border-radius: 4px;
      &.popcancel{
        margin:0 40px;
        border: 1px solid #26a2ff;
        color: orange;
      }
      &.popconfirm{
        margin:0 40px;
        border: 1px solid orange;
        color: white;
        background: orange;
      }
    }
    .rightText{
      flex: 1;
      border:gainsboro 1px solid;
      border-radius: 4px;
      padding: 15px 10px;
    }
  }
}

</style>
