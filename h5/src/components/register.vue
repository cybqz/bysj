<template>
  <div class="popLogin">
    <div class="loginWrap">
      <div class="user">
        <input class="rightText" type="text" maxlength='15' placeholder="请输入用户名" v-model="userName">
      </div>
      <div class="password">
        <input class="rightText" type="password" maxlength='15' placeholder="请输入密码" v-model="password">
      </div>
      <div class="btn" @click="login">
        注册
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'register',
  data () {
    return {
      userName:'',
      password:'',
    }
  },
  components:{
  },
  methods:{
    //注册
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
          url:'register/register',
          data:$this.qs.stringify({    //这里是发送给后台的数据
                userName:this.userName,
                password:this.password
          })
      }).then((response) =>{   //这里使用了ES6的语法
          if(response.data.code =='200'){
            this.$Message.success(response.data.msg);
            $this.$router.push({
              path: '/login',
            })
          }else{
            this.$Message.error(response.data.msg);
          }
      })
    },
    
  },
  mounted(){
  }
  
}
</script>

<style scoped lang='less'>

.pad30{
  padding:  0 0.3rem;
}
.shadow{
  box-shadow: inset 0 0 0.03rem 0 gainsboro;
  border-radius: 0.1rem;
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
 .user,.password{
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
