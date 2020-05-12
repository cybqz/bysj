<template>
  <div class="popLogin">
    <div class="loginWrap">
      <div class="user">
        <div class="title">
          用户名:
        </div>
        <input class="rightText" type="text" maxlength='15' placeholder="请输入用户名" v-model="userName">
      </div>
      <div class="password">
        <div class="title">
          密码:
        </div>
        <input class="rightText" type="password" maxlength='15' placeholder="请输入密码" v-model="password">
      </div>
      <div class="btnWrap">
        <div class="btn putHover" @click="login">
          登录
        </div>
      </div>
      <div class="btnWrap">
        <div class="btn orange putHover" @click="register">
          注册
        </div>
      </div>
      
    </div>
  </div>
</template>

<script>
export default {
  name: 'shareList',
  data () {
    return {
      userName:'',
      password:'',
      
    }
  },
  components:{
  },
  methods:{
    register(){
      this.$router.push({
        path: '/register',
      })
    },
    login(){
      if(!this.userName){
        this.$Message.error(
          {
            content: '用户名不能为空',
            duration: 2000
          }
        );
        return;
      }
      if(!this.password){
        this.$Message.error(
          {
            content: '密码不能为空',
            duration: 2000
          }
        );
        return
      }
      let $this = this
      this.$axios({
          method:'post',
          url:'login/login',
          data:$this.qs.stringify({    //这里是发送给后台的数据
                userName:this.userName,
                password:this.password,
          })
      }).then((response) =>{  
          if(response.data.code =='200'){
            sessionStorage.setItem("token",response.data.data.authToken);
            sessionStorage.setItem("userName",response.data.data.userName);
            $this.$router.push({
              path: '/',
            })
            this.$router.go(0);
          }else{
            this.$Message.error( response.data.msg);
          }  
          
      })
    },
    
  },
  mounted(){
  }
  
}
</script>

<style scoped lang='less'>
&.orange{
  background: orange;
}
.popLogin{
    width: 100%;
    height: 100%;
    padding-top: 60px;
    display: flex;
    justify-content: center;
    align-items: center;
    background: url(../assets/images/bg1.jpg) no-repeat center;
    background-size: cover;
    position: fixed;
     bottom: 0;
    right: 0;
    left: 0;
    top: 0;
    z-index: 999;

}
.loginWrap{
 .user,.password,.btnWrap{
   width: 280px;
   margin-bottom: 60px;
   display: flex;
   align-items: center;
 }
 .rightText{
    flex: 1;
    width: 200px;
    border:gainsboro 1px solid;
    border-radius: 1px;
    height: 30px;
    border-radius: 5px;
    padding-left: 50px;
    background: white;
    opacity: .5;
    font-size: 14px;
  }
  .title{
    font-size: 14px;
    width:50px;
    margin-right: 10px;
    text-align: left;
    color: white;
  }
  .btnWrap{
    .title{
      color: orange;
    }
  }
  .btn{
    flex: 1;
   height: 30px;
   color: white;
   font-size: 14px;
   border-radius: 5px;
   line-height: 30px;
   text-align: center;
   background:#00a6ff;
   &.orange{
    background: orange;
  }
 }
}
</style>
