<template>
  <div>
    <div class="userWrap">
        <div class="user_div">
            <span class="userName">用户名：</span>
            <input disabled v-model="userName" class="userInput" type="text">
        </div>
        <div class="user_div">
            <span class="userName">旧密码：</span>
            <input class="userInput" v-model="password" placeholder="请输入旧密码" type="password">
        </div>
        <div class="user_div">
            <span class="userName">新密码：</span>
            <input class="userInput" v-model="passwordNew" placeholder="请输入新密码" type="password">
        </div>
        <div class="user_div">
            <span class="userName">确认密码：</span>
            <input class="userInput"  v-model="passwordNewAgain" placeholder="请再次输入新密码" type="password">
        </div>
        <div class="user_div">
            <span class="userBtn putHover" @click="cancel()">取消</span>
            <span class="userBtn userConfirm putHover" @click="confirm()">确认</span>
        </div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'password',
  data () {
    return {
      userName:'',
      password:'',
      passwordNew:'',
      passwordNewAgain:'',
      
    }
  },
  components:{
  },
  methods:{
    cancel(){
        this.$router.go(-1)
    },
    confirm(){
        if(!this.password){
            this.$Message.error(
            {
                content: '旧密码不能为空',
            }
            );
            return
        }
        if(!this.passwordNew){
            this.$Message.error(
            {
                content: '新密码不能为空',
            }
            );
            return
        }
        if(!this.passwordNewAgain){
            this.$Message.error(
            {
                content: '确认密码不能为空',
            }
            );
            return
        }
        if(this.password == this.passwordNew){
            this.$Message.error(
            {
                content: '新密码不能与旧密码相同',
            }
            );
            return
        }
        if(this.passwordNewAgain != this.passwordNew){
            this.$Message.error(
            {
                content: '确认密码与新密码没有保持一致',
            }
            );
            return
        }
        let $this = this
      this.$axios({
          method:'post',
          url:'user/updatePassword',
          data:$this.qs.stringify({    //这里是发送给后台的数据
                oldPassword:this.password,
                password:this.passwordNew
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
    }
  },
  mounted(){
      this.userName = sessionStorage.getItem('userName')
  }
  
}
</script>

<style scoped lang='less'>
.userWrap{
    color: orange;
    padding: 60px;
    width: 60%;
    margin: 60px auto;
}
.user_div{
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    padding: 10px;
    margin-bottom: 60px;
}
.userInput{
    flex: 1;
    height: 40px;
    flex: 1;
    border:gainsboro 1px solid;
    border-radius:  3px 0 0 3px;
    padding: 15px 5px;
}
.userName{
    width: 150px;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    margin-right: 20px;
}
.userBtn{
    display: flex;
    width: 100px;
    height: 40px;
    align-items: center;
    justify-content: center;
    margin: 30px;
    border-radius: 4px;
    border: orange 1px solid;
}
.userConfirm{
    background: orange;
    color: white;
}
</style>
