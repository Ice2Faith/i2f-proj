<template>
  <div>
    <van-row style="height: 25vw;border: solid 1px #aaa;margin:5px;padding: 5px;border-radius: 5px">
      <van-col :span="8" style="height: 100%">
        <van-image
          fit="cover"
          height="25vw"
          round
          src="/favicon.ico"
          width="25vw"
        />
      </van-col>
      <van-col :span="16" style="height: 100%;padding-left: 15px">
        <van-row style="height: 60%;border-bottom: solid 1px #777">
              <span style="position: relative;top: 50%;transform: translateY(-50%);height: 50%">
                {{ user.tag.realname }}
              </span>
        </van-row>
        <van-row style="height: 40%">
          <van-row style="position: relative;top: 50%;transform: translateY(-50%);height: 60%">
            <van-button hairline plain size="mini" type="primary">个人信息</van-button>
            <van-button hairline plain size="mini" style="margin-left: 20px" type="success">修改密码</van-button>
          </van-row>
        </van-row>
      </van-col>
    </van-row>

    <van-cell-group title="账号">
      <van-cell is-link title="退出登录" @click="logout"/>
    </van-cell-group>

    <van-cell-group title="系统">
      <van-cell is-link title="关于" to="/about"/>
    </van-cell-group>
  </div>
</template>
<script>
import Auth from '@/framework/auth'

export default {
  name: 'MineView',
  data () {
    return {
      user: {}
    }
  },
  created () {
    this.user = Auth.getUser()
  },
  methods: {
    logout () {
      this.$axios({
        url: '/logout',
        method: 'get'
      }).then(data => {
        this.$router.replace({ path: '/' })
        this.$message.noticeInfo(data.msg)
      })
    }
  }
}
</script>
<style scoped>

</style>
