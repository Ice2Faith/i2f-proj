<template>
  <div>
    <a-form
      ref="form"
      :label-col="{ span: 6 }"
      :model="form"
      :wrapper-col="{ span: 18 }"
      autocomplete="off"
      layout="inline"
      name="basic"
      @keyup.enter="doSearch"
    >

      <a-form-item
        label="播放地址"
        name="url"
        style="width: 100%"
      >
        <a-input v-model:value="form.url" allow-clear/>
      </a-form-item>


      <a-form-item :wrapper-col="{ offset: 0, span: 24 }">
        <a-row :gutter="20" justify="center" type="flex">
          <a-col>
            <a-spin :spinning="queryLoading">
              <a-button type="primary" @click="doSearch">
                <template #icon>
                  <SearchOutlined/>
                </template>
                搜索
              </a-button>
            </a-spin>
          </a-col>
          <a-col>
            <a-button @click="doReset">
              <template #icon>
                <clear-outlined/>
              </template>
              重置
            </a-button>
          </a-col>
          <a-col>
            <a-button
              style="background-color: #444;color: white"
              @click="dialogs.pushConfig.show=true">
              <template #icon>
                <RocketOutlined/>
              </template>
              推流
            </a-button>
          </a-col>
        </a-row>
      </a-form-item>
    </a-form>
    <div style="width: 100%;height: 50vh">
      <video-player
        ref="videoPlayer"
        :autoplay="true"
        :controls="true"
        :fluid="true"
        :loop="true"
        :muted="false"
        :src="form.url"
        :volume="0.6"
        language="zh-CN"
        poster="/your-path/poster.jpg"
        @ended="onPlayerEnded"
        @pause="onPlayerPause"
        @play="onPlayerPlay"
        @ready="onPlayerReady"
        @seeking="onPlayerSeeking">
      </video-player>
    </div>
    <a-drawer
      v-model:visible="dialogs.pushConfig.show"
      :title="dialogs.pushConfig.title"
      placement="right"
      width="46%"
    >
      <a-form
        :label-col="{ span: 4 }"
        :wrapper-col="{ span: 20 }"
        autocomplete="off"
      >
        <a-checkbox v-model:checked="metas.useFfmpeg">FFMPEG</a-checkbox>

        <a-form-item
          label="服务器"
          name="host"
        >
          <a-input v-model:value="form.host" allow-clear/>
        </a-form-item>
        <a-form-item
          label="推流通道"
          name="pushChannel"
        >
          <a-input v-model:value="form.pushChannel" allow-clear/>
        </a-form-item>
        <a-form-item
          label="授权码"
          name="token"
        >
          <a-input v-model:value="form.token" allow-clear/>
        </a-form-item>

        <a-form-item
          label="HLS(m3u8)"
        >
        </a-form-item>
        <a-form-item
          label="推流服务器"
          name="pushHlsServerUrl"
        >
          <a-input :value="pushHlsServerUrl" allow-clear disabled/>
        </a-form-item>
        <a-form-item
          label="推流码"
          name="pushHlsServerCode"
        >
          <a-input :value="pushHlsServerCode" allow-clear disabled/>
        </a-form-item>
        <a-form-item
          label="推流地址"
          name="pushHlsUrl"
        >
          <a-input :value="pushHlsUrl" allow-clear disabled/>
        </a-form-item>
        <a-form-item
          label="拉流地址"
          name="pullHlsUrl"
        >
          <a-input :value="pullHlsUrl" allow-clear disabled/>
        </a-form-item>


        <a-form-item
          label="RTMP(flv)"
        >
        </a-form-item>
        <a-form-item
          label="推流服务器"
          name="pushRtmpServerUrl"
        >
          <a-input :value="pushRtmpServerUrl" allow-clear disabled/>
        </a-form-item>
        <a-form-item
          label="推流码"
          name="pushRtmpServerCode"
        >
          <a-input :value="pushRtmpServerCode" allow-clear disabled/>
        </a-form-item>
        <a-form-item
          label="推流地址"
          name="pushRtmpUrl"
        >
          <a-input :value="pushRtmpUrl" allow-clear disabled/>
        </a-form-item>
        <a-form-item
          label="拉流地址"
          name="pullRtmpUrl"
        >
          <a-input :value="pullRtmpUrl" allow-clear disabled/>
        </a-form-item>

        <a-form-item
          label="推流工具"
        >
        </a-form-item>
        <a-form-item :wrapper-col="{ offset: 0, span: 24 }">
          <a-row :gutter="20" justify="center" type="flex">
            <a-col>
              <a-button
                href="https://obsproject.com/zh-cn"
                style="background-color: #444;color: white"
                target="_blank">
                <template #icon>
                  <RocketOutlined/>
                </template>
                OBS Studio
              </a-button>
            </a-col>
            <a-col>
              <a-button
                href="https://ffmpeg.org/download.html"
                style="background-color: #0a4;color: white"
                target="_blank">
                <template #icon>
                  <CodeOutlined/>
                </template>
                FFMPEG
              </a-button>
            </a-col>
          </a-row>
        </a-form-item>


        <a-form-item
          label="拉流工具"
        >
        </a-form-item>
        <a-form-item :wrapper-col="{ offset: 0, span: 24 }">
          <a-row :gutter="20" justify="center" type="flex">
            <a-col>
              <a-button
                href="https://www.videolan.org/"
                style="background-color: #fa0;color: white"
                target="_blank">
                <template #icon>
                  <YoutubeOutlined/>
                </template>
                VLC Player
              </a-button>
            </a-col>
            <a-col>
              <a-button
                href="https://potplayer.org/category-3.html"
                style="background-color: #dd0;color: white"
                target="_blank">
                <template #icon>
                  <YoutubeOutlined/>
                </template>
                PotPlayer
              </a-button>
            </a-col>
            <a-col>
              <a-button
                href="https://ffmpeg.org/download.html"
                style="background-color: #0a4;color: white"
                target="_blank">
                <template #icon>
                  <CodeOutlined/>
                </template>
                FFPLAY
              </a-button>
            </a-col>
          </a-row>
        </a-form-item>
      </a-form>
    </a-drawer>
  </div>
</template>
<script>
import {VideoPlayer} from '@videojs-player/vue'
import 'video.js/dist/video-js.css'
import ListManageMixin from "@/mixins/ListManageMixin";
import Auth from "@/framework/auth";

export default {
  components: {
    VideoPlayer
  },
  mixins: [ListManageMixin],
  data() {
    return {
      moduleBaseUrl: '/api/biz/liveOnline',
      user: {
        username: 'hello'
      },

      form: {
        url: 'http://47.92.88.61:11934/hls/hello/index.m3u8?token=ltb12315',

        host: '47.92.88.61',
        token: 'ltb12315',
        pushChannel: 'hello'
      },
      rules: {},
      dialogs: {
        pushConfig: {
          show: false,
          title: '推流配置'
        }
      },
      metas: {
        useFfmpeg: false,
      },
      tableColumns: []
    }
  },
  mounted() {
    this.user = Auth.getUser()
    this.form.pushChannel = this.user.username
  },
  computed: {
    pushHlsServerUrl() {
      return `rtmp://${this.form.host}:11935/hls/`
    },
    pushHlsServerCode() {
      return `${this.form.pushChannel}?token=${this.form.token}`
    },
    pushHlsUrl() {
      let url = `rtmp://${this.form.host}:11935/hls/${this.form.pushChannel}?token=${this.form.token}`
      if (this.metas.useFfmpeg) {
        url = `ffmpeg -re -i test.mp4 -f flv ${url}`
      }
      return url
    },
    pullHlsUrl() {
      let url = `http://${this.form.host}:11934/hls/${this.form.pushChannel}/index.m3u8?token=${this.form.token}`
      if (this.metas.useFfmpeg) {
        url = `ffplay ${url}`
      }
      return url
    },
    pushRtmpServerUrl() {
      return `rtmp://${this.form.host}:11935/live/`
    },
    pushRtmpServerCode() {
      return `${this.form.pushChannel}?token=${this.form.token}`
    },
    pushRtmpUrl() {
      let url = `rtmp://${this.form.host}:11935/live/${this.form.pushChannel}?token=${this.form.token}`
      if (this.metas.useFfmpeg) {
        url = `ffmpeg -re -i test.mp4 -f flv ${url}`
      }
      return url
    },
    pullRtmpUrl() {
      let url = `rtmp://${this.form.host}:11935/live/${this.form.pushChannel}?token=${this.form.token}`
      if (this.metas.useFfmpeg) {
        url = `ffplay ${url}`
      }
      return url
    }
  },
  methods: {
    onPlayerReady() {

    },
    onPlayerPlay() {

    },
    onPlayerPause() {

    },
    onPlayerEnded() {

    },
    onPlayerSeeking() {

    },
    doSearch() {

    }
  }
}
</script>
<style>

</style>
