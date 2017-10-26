<template>
  <div>
    <Form ref="searchAuthorityForm" :model="searchAuthorityFormModel" inline>
      <FormItem prop="authorityMark">
          <Input type="text" v-model="searchAuthorityFormModel.authorityMark" placeholder="权限标识">
          </Input>
      </FormItem>
      <FormItem prop="authorityName">
          <Input type="text" v-model="searchAuthorityFormModel.authorityName" placeholder="权限名称">
          </Input>
      </FormItem>
      <FormItem prop="message">
          <Input type="text" v-model="searchAuthorityFormModel.message" placeholder="提示信息">
          </Input>
      </FormItem>
      <FormItem prop="createdDateStart">
          <DatePicker type="date" format="yyyy-MM-dd" v-model="searchAuthorityFormModel.createdDateStart" placeholder="创建开始日期" style="width: 120px"></DatePicker>
      </FormItem>
      <FormItem prop="createdDateEnd">
          <DatePicker type="date" format="yyyy-MM-dd" v-model="searchAuthorityFormModel.createdDateEnd" placeholder="创建结束日期" style="width: 120px"></DatePicker>
      </FormItem>
      <FormItem>
          <Button type="primary" icon="ios-search" @click="formHandleSubmit('searchAuthorityForm')">搜索</Button>
          <Button type="ghost" style="background-color: rgb(4, 236, 118);" icon="ios-plus-outline" @click="showDetailAuthorityModalBtn('')">新增</Button>
      </FormItem>
    </Form>
    <Tabs v-model="currentTabsValue" :size="tableSize" >
        <TabPane label="权限列表" icon="android-list" name="tabPaneAuthority">
            <Table :data="tableAuthorityData" border :loading="loading" :size="tableSize" :columns="tableAuthorityColumns" height="435"></Table>
            <div style="margin: 10px;overflow: hidden">
                <div style="float: right;">
                    <Page :size="tableSize" :total="totalNum" :current="currentPage" @on-change="pageChange" @on-page-size-change="pageSizeChange" show-elevator show-sizer show-total></Page>
                </div>
            </div>
        </TabPane>
        <TabPane label="资源信息" icon="connection-bars"  name="tabPaneAuthorityResources">
            <Form ref="searchAuthorityResourcesForm" :model="searchAuthorityResourcesFormModel" style="margin-left: 20px" inline>
              <FormItem prop="resourceName">
                  <Input type="text" :size="tableSize" v-model="searchAuthorityResourcesFormModel.resourceName" placeholder="资源名称">
                  </Input>
              </FormItem>
              <FormItem>
                  <Button type="info" icon="ios-search" :size="tableSize" @click="formHandleSubmit('searchAuthorityResourcesForm')">搜索资源</Button>
              </FormItem>
              <FormItem>
                  <Button type="primary" icon="ios-plus-outline" :size="tableSize" @click="addAuthorityResourcesBtn()">添加资源</Button>
              </FormItem>
            </Form>
            <Table :data="tableAuthorityResourcesData" border :loading="resourcesLoading" :size="tableSize" :columns="tableAuthorityResourcesColumns" height="435"></Table>
            <div style="margin: 10px;overflow: hidden">
                <div style="float: right;">
                    <Page :size="tableSize" :total="resourcesTotalNum" :current="resourcesCurrentPage" @on-change="resourcesPageChange" @on-page-size-change="resourcesPageSizeChange" show-elevator show-sizer show-total></Page>
                </div>
            </div>

        </TabPane>
    </Tabs>

    <Modal v-model="detailAuthorityModal" width="880" :mask-closable="false" :scrollable="true">
        <p slot="header" style="color:#3399ff;text-align:left">
            <Icon type="icon-addpeople_fill"></Icon>
            <span>权限信息</span>
        </p>

        <Form ref="detailAuthorityForm" :model="detailAuthorityFormModel" :rules="detailAuthorityFormRule" :label-width="75" label-position="right" >

          <Row  style="text-align: right">
              <Col span="12">
                <FormItem label="权限代码：" prop="authorityId">
                    <Input type="text" v-model="detailAuthorityFormModel.authorityId" disabled ></Input>
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem label="权限标识:" prop="authorityMark">
                    <Input type="text" v-model="detailAuthorityFormModel.authorityMark" placeholder="请输入权限标识">
                    </Input>
                </FormItem>
              </Col>

          </Row>

          <Row>
            <Col span="12">
              <FormItem label="权限名称:" prop="authorityName">
                <Input v-model="detailAuthorityFormModel.authorityName" placeholder="请输入权限名称"></Input>
              </FormItem>
            </Col>
            <Col span="6">
                <FormItem label="是否可用:" prop="enable">
                  <i-switch v-model="detailAuthorityFormModel.enable" size="large">
                      <span slot="open">可用</span>
                      <span slot="close">不可</span>
                  </i-switch>
                </FormItem>
            </Col>
            <Col span="6">
              <FormItem label="系统资源:" prop="issys">
                <i-switch v-model="detailAuthorityFormModel.issys" size="large">
                    <span slot="open">是</span>
                    <span slot="close">否</span>
                </i-switch>
              </FormItem>
            </Col>
          </Row>

          <Row  style="text-align: right">
              <Col span="12">
                <FormItem label="提示信息:" prop="message">
                    <Input type="textarea" v-model="detailAuthorityFormModel.message" :rows="2" placeholder="请输入提示信息"></Input>
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem label="权限说明:" prop="authorityDesc">
                    <Input type="textarea" v-model="detailAuthorityFormModel.authorityDesc" :rows="2" placeholder="请输入权限说明"></Input>
                </FormItem>
              </Col>
          </Row>

          <Row>
              <Col span="5">
                <FormItem label="创建人：" prop="createdBy" :label-width="70">
                  <Input v-model="detailAuthorityFormModel.createdBy" disabled ></Input>
                </FormItem>
              </Col>
              <Col span="6">
                <FormItem label="创建时间：" prop="createdDate" :label-width="75">
                  <Input v-model="detailAuthorityFormModel.createdDate" disabled></Input>
                </FormItem>
              </Col>
              <Col span="6">
                <FormItem label="最后更新人：" prop="lastModifiedBy" :label-width="85">
                  <Input v-model="detailAuthorityFormModel.lastModifiedBy" disabled></Input>
                </FormItem>
              </Col>
              <Col span="7">
                <FormItem label="最后更新时间：" prop="lastModifiedDate" :label-width="115">
                  <Input v-model="detailAuthorityFormModel.lastModifiedDate" disabled></Input>
                </FormItem>
              </Col>
          </Row>
          <Row  style="text-align: center">
            <FormItem>
                  <Button type="primary" @click="formHandleSubmit('detailAuthorityForm')">提交</Button>
                  <Button type="ghost" @click="resetForm('detailAuthorityForm')" style="margin-left: 38px">重置</Button>
            </FormItem>
          </Row>
        </Form>

        <div slot="footer" style="text-align:right">
        </div>
    </Modal>

    <Modal v-model="addAuthorityResourcesModal" width="880" :mask-closable="false" :scrollable="true">
        <p slot="header" style="color:#3399ff;text-align:left">
            <Icon type="icon-addpeople_fill"></Icon>
            <span>权限关联资源</span>
        </p>
        <Form ref="searchAddAuthorityResourcesForm" :model="searchAddAuthorityResourcesFormModel" inline  label-position="right" >
              <FormItem prop="resourceName">
                  <Input :size="tableSize" v-model="searchAddAuthorityResourcesFormModel.resourceName" placeholder="资源名称"></Input>
              </FormItem>
              <FormItem>
                  <Button type="info" icon="ios-search" :size="tableSize" @click="formHandleSubmit('searchAddAuthorityResourcesForm')">搜索资源</Button>
              </FormItem>
              <FormItem>
                  <Button type="warning" icon="ios-plus-outline" :size="tableSize" @click="addAuthorityResourcesRequestBtn()">添加资源到当前权限</Button>
              </FormItem>
        </Form>
        <Table :data="addAuthorityResourcesTableData" border :loading="addAuthorityResourcesTableLoading"
        :size="tableSize" :columns="addAuthorityResourcesTableColumns" height="435"
        @on-selection-change="addAuthorityResourcesTableSelectionChange"></Table>
        <div style="margin: 10px;overflow: hidden">
            <div style="float: right;">
                <Page :size="tableSize" :total="addAuthorityResourcesTotalNum" :current="addAuthorityResourcesCurrentPage" @on-change="addAuthorityResourcesPageChange" @on-page-size-change="addAuthorityResourcesPageSizeChange" show-elevator show-sizer show-total></Page>
            </div>
        </div>
        <div slot="footer" style="text-align:right">
        </div>
    </Modal>
  </div>

</template>
<script>
  import {hierarchicalEdgeBundling} from 'vued3tree'
  import { getAuthorityStringRegex } from '@/common/utils'

  export default {
    name: 'authoritieslist',
    components: {
      hierarchicalEdgeBundling
    },
    data () {
      return {
        addAuthorityResourcesTableSelectedData: [],
        addAuthorityResourcesTableData: [],
        searchAddAuthorityResourcesFormModel: {
          resourceName: '',
          page: 0,
          size: 10
        },
        addAuthorityResourcesTotalNum: 100,
        addAuthorityResourcesCurrentPage: 1,
        addAuthorityResourcesTableColumns: [
          { // @:columns
            type: 'selection', // 开启checkbox
            width: 65,
            fixed: 'left',
            key: 'selectResource',
            align: 'center'
          },
          {
            title: '资源名称',
            key: 'resourceName',
            align: 'left',
            width: 155,
            fixed: 'left',
            sortable: true
          },
          {
            title: '唯一标识',
            key: 'globalUniqueId',
            width: 255,
            align: 'left'
          },
          {
            title: '资源类型',
            align: 'left',
            width: 105,
            key: 'resourceType'
          },
          {
            title: '请求方法',
            align: 'left',
            width: 105,
            key: 'requestMethod'
          },
          {
            title: '是否可用',
            align: 'left',
            width: 95,
            key: 'enable',
            render: (h, params) => {
              const row = params.row
              const color = row.enable === true ? 'green' : 'red'
              const type = row.enable === true ? 'checkmark-circled' : 'close-circled'
              // const text = row.enable === true ? '可用' : '不可用'

              return h('Icon', {
                props: {
                  type: type,
                  color: color,
                  size: 16
                }
              })
            }
          },
          {
            title: '系统资源',
            align: 'left',
            width: 95,
            key: 'issys',
            render: (h, params) => {
              const row = params.row
              const color = row.issys === true ? 'green' : 'blue'
              const type = row.issys === true ? 'checkmark-circled' : 'close-circled'
              // const text = row.issys === true ? '是' : '否'
              const text = ''
              return h('Icon', {
                props: {
                  type: type,
                  color: color,
                  size: 16
                }
              }, text)
            }
          },
          {
            title: '资源路径',
            align: 'left',
            width: 235,
            key: 'resourcePath'
          },
          {
            title: '资源代码',
            key: 'resourceId',
            width: 260,
            align: 'left'
          },
          {
            title: '父资源代码',
            align: 'left',
            width: 260,
            key: 'fatherResourceId'
          },
          {
            title: '图标',
            align: 'left',
            width: 110,
            key: 'icon'
          },
          {
            title: '优先级',
            align: 'left',
            width: 90,
            key: 'priority'
          },
          {
            title: '资源描述',
            align: 'left',
            width: 350,
            key: 'resourceDesc'
          },
          {
            title: '创建人',
            align: 'left',
            width: 100,
            key: 'createdBy'
          },
          {
            title: '创建时间',
            align: 'left',
            width: 155,
            key: 'createdDate'
          },
          {
            title: '最后更新人',
            align: 'left',
            width: 100,
            key: 'lastModifiedBy'
          },
          {
            title: '最后更新时间',
            align: 'left',
            width: 155,
            key: 'lastModifiedDate'
          }
        ],
        addAuthorityResourcesTableLoading: false,
        addAuthorityResourcesModal: false,
        detailAuthorityFormRule: {
          authorityMark: [
            { required: true, type: 'string', message: '权限标识必须以 "AUTH_" 开头', trigger: 'blur', pattern: getAuthorityStringRegex() }
          ],
          authorityName: [
            { required: true, message: '资源名称不能为空', trigger: 'blur' }
          ],
          message: [
            { required: true, message: '父资源代码不能为空', trigger: 'blur' }
          ],
          enable: [
            { type: 'boolean', required: true, message: '请选择是否可用', trigger: 'blur' }
          ]
        },
        detailAuthorityFormModel: {
          authorityId: '',
          authorityMark: '',
          authorityName: '',
          enable: true,
          issys: false,
          message: '',
          authorityDesc: '',
          createdBy: '',
          createdDate: '',
          lastModifiedBy: '',
          lastModifiedDate: ''
        },
        detailAuthorityModal: false,
        queryAuthorityId: '',
        tableAuthorityResourcesData: [],
        tableAuthorityResourcesColumns: [
          {
            title: '资源名称',
            key: 'resourceName',
            align: 'left',
            fixed: 'left',
            width: 190,
            sortable: true
          },
          {
            title: '唯一标识',
            key: 'globalUniqueId',
            width: 260,
            align: 'left',
            sortable: true
          },
          {
            title: '资源类型',
            align: 'left',
            width: 110,
            key: 'resourceType',
            sortable: true
          },
          {
            title: '请求方法',
            align: 'left',
            width: 110,
            key: 'requestMethod'
          },
          {
            title: '是否可用',
            align: 'left',
            width: 100,
            key: 'enable',
            render: (h, params) => {
              const row = params.row
              const color = row.enable === true ? 'green' : 'red'
              const type = row.enable === true ? 'checkmark-circled' : 'close-circled'
              // const text = row.enable === true ? '可用' : '不可用'

              return h('Icon', {
                props: {
                  type: type,
                  color: color,
                  size: 16
                }
              })
            }
          },
          {
            title: '系统资源',
            align: 'left',
            width: 100,
            key: 'issys',
            render: (h, params) => {
              const row = params.row
              const color = row.issys === true ? 'green' : 'blue'
              const type = row.issys === true ? 'checkmark-circled' : 'close-circled'
              // const text = row.issys === true ? '是' : '否'
              const text = ''
              return h('Icon', {
                props: {
                  type: type,
                  color: color,
                  size: 16
                }
              }, text)
            }
          },
          {
            title: '资源路径',
            align: 'left',
            width: 350,
            key: 'resourcePath'
          },
          {
            title: '资源代码',
            key: 'resourceId',
            width: 260,
            align: 'left'
          },
          {
            title: '父资源代码',
            align: 'left',
            width: 260,
            key: 'fatherResourceId'
          },
          {
            title: '图标',
            align: 'left',
            width: 150,
            key: 'icon'
          },
          {
            title: '优先级',
            align: 'left',
            width: 100,
            key: 'priority'
          },
          {
            title: '资源描述',
            align: 'left',
            width: 350,
            key: 'resourceDesc'
          },
          {
            title: '创建人',
            align: 'left',
            width: 100,
            key: 'createdBy'
          },
          {
            title: '创建时间',
            align: 'left',
            width: 150,
            key: 'createdDate'
          },
          {
            title: '最后更新人',
            align: 'left',
            width: 100,
            key: 'lastModifiedBy'
          },
          {
            title: '最后更新时间',
            align: 'left',
            width: 180,
            key: 'lastModifiedDate'
          },
          {
            title: '操作',
            width: 80,
            fixed: 'right',
            align: 'left',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'error',
                    size: this.tableSize
                  },
                  style: {
                    color: '#3c3c3c'
                  },
                  on: {
                    click: () => {
                      console.log(params)
                      this.deleteAuthorityResources(params.row.resourceId, params.index)
                    }
                  }
                }, '删除')
              ])
            }
          }
        ],
        resourcesLoading: false,
        resourcesTotalNum: 100,
        resourcesCurrentPage: 1,
        searchAuthorityResourcesFormModel: {
          resourceName: '',
          page: 0,
          size: 10
        },
        currentTabsValue: 'tabPaneAuthority',
        tableAuthorityData: [],
        loading: false,
        tableSize: 'small',
        totalNum: 100,
        currentPage: 1,
        tableAuthorityColumns: [
          {
            title: '权限标识',
            key: 'authorityMark',
            align: 'left',
            width: 230,
            fixed: 'left',
            sortable: true
          },
          {
            title: '权限名称',
            align: 'left',
            width: 220,
            key: 'authorityName'
          },
          {
            title: '权限代码',
            key: 'authorityId',
            width: 250,
            align: 'left'
          },
          {
            title: '提示信息',
            align: 'message',
            width: 250,
            key: 'name'
          },
          {
            title: '是否可用',
            align: 'left',
            width: 90,
            key: 'enable',
            render: (h, params) => {
              const row = params.row
              const color = row.enable === true ? 'green' : 'red'
              const type = row.enable === true ? 'checkmark-circled' : 'close-circled'
              // const text = row.enable === true ? '可用' : '不可用'

              return h('Icon', {
                props: {
                  type: type,
                  color: color,
                  size: 16
                }
              })
            }
          },
          {
            title: '系统权限',
            align: 'left',
            width: 90,
            key: 'issys',
            render: (h, params) => {
              const row = params.row
              const color = row.issys === true ? 'green' : 'blue'
              const type = row.issys === true ? 'checkmark-circled' : 'close-circled'
              // const text = row.issys === true ? '是' : '否'
              const text = ''
              return h('Icon', {
                props: {
                  type: type,
                  color: color,
                  size: 16
                }
              }, text)
            }
          },
          {
            title: '模块代码',
            align: 'left',
            width: 120,
            key: 'moduleId'
          },
          {
            title: '创建人',
            align: 'left',
            width: 80,
            key: 'createdBy'
          },
          {
            title: '创建时间',
            align: 'left',
            width: 150,
            key: 'createdDate'
          },
          {
            title: '最后更新人',
            align: 'left',
            width: 100,
            key: 'lastModifiedBy'
          },
          {
            title: '最后更新时间',
            align: 'left',
            width: 150,
            key: 'lastModifiedDate'
          },
          {
            title: '操作',
            key: 'action',
            width: 220,
            fixed: 'right',
            align: 'left',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'primary',
                    size: this.tableSize
                  },
                  style: {
                    color: '#3c3c3c'
                  },
                  on: {
                    click: () => {
                      // this.$Message.info({content: JSON.stringify(params), duration: 10})
                      this.showDetailAuthorityModalBtn(params.row)
                    }
                  }
                }, '编辑'),
                h('Button', {
                  props: {
                    type: 'warning',
                    size: this.tableSize
                  },
                  style: {
                    color: '#3c3c3c'
                  },
                  on: {
                    click: () => {
                      this.showTabPaneAuthorityResources(params.row.authorityId)
                    }
                  }
                }, '资源信息'),
                h('Button', {
                  props: {
                    type: 'error',
                    size: this.tableSize
                  },
                  style: {
                    color: '#3c3c3c'
                  },
                  on: {
                    click: () => {
                      console.log(params)
                      this.removeAuthority(params.row.authorityId, params.index)
                    }
                  }
                }, '删除')
              ])
            }
          }
        ],
        searchAuthorityFormModel: {
          authorityMark: '',
          authorityName: '',
          message: '',
          createdDateStart: '',
          createdDateEnd: '',
          page: 0,
          size: 10
        }
      }
    },
    mounted: function () {
      this.queryAuthorityForm()
    },
    methods: {
      queryAuthorityForm () {
        this.formHandleSubmit('searchAuthorityForm')
      },
      formHandleSubmit (name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            if (name === 'searchAuthorityForm') {
              this.loading = true
              console.log('查询参数：')
              console.log(this.searchAuthorityFormModel)

              this.$api.requestUtil('/authority/list', 'post', this.searchAuthorityFormModel).then(res => {
                console.log('查询结果:')
                console.log(res)
                if (res.responseCode === '00000') {
                  this.tableAuthorityData = res.data.content
                  this.totalNum = res.data.totalElements
                }
              }).catch(err => {
                console.error('哎哟~！', err)
              })
              this.loading = false
            } else if (name === 'searchAuthorityResourcesForm') {
              this.requestAuthorityResources()
            } else if (name === 'detailAuthorityForm') {
              this.$api.requestUtil('/authority', 'post', this.detailAuthorityFormModel).then(res => {
                console.log('新增结果:')
                console.log(res)
                if (res.responseCode === '00000') {
                  this.detailAuthorityFormModel = res.data
                  this.$Message.success('提交成功!')
                  this.queryAuthorityForm()
                }
              })
            } else if (name === 'searchAddAuthorityResourcesForm') {
              this.requestAuthorityResourcesForAdd()
            }
          } else {
            this.$Message.error('表单验证失败!')
          }
        })
      },
      pageChange (page) {
        // 这里直接更改了模拟的数据，真实使用场景应该从服务端获取数据
        this.searchAuthorityFormModel.page = page
        this.formHandleSubmit('searchAuthorityForm')
      },
      pageSizeChange (size) {
        this.searchAuthorityFormModel.size = size
        this.formHandleSubmit('searchAuthorityForm')
      },
      showTabPaneAuthorityResources (authorityId) {
        this.currentTabsValue = 'tabPaneAuthorityResources'
        this.queryAuthorityId = authorityId
        this.requestAuthorityResources()
      },
      requestAuthorityResources () {
        this.resourcesLoading = true
        this.$api.requestUtil('/authority/andresource/' + this.queryAuthorityId + '?resourceName=' + this.searchAuthorityResourcesFormModel.resourceName + '&page=' + this.searchAuthorityResourcesFormModel.page + '&size=' + this.searchAuthorityResourcesFormModel.size, 'get', '').then(res => {
          console.log('查询结果:')
          console.log(res)
          if (res.responseCode === '00000') {
            this.tableAuthorityResourcesData = res.data.resources.content
            this.resourcesTotalNum = res.data.resources.totalElements
          }
        }).catch(err => {
          console.error('哎哟~！', err)
        })
        this.resourcesLoading = false
      },
      resourcesPageChange (page) {
        // 这里直接更改了模拟的数据，真实使用场景应该从服务端获取数据
        this.searchAuthorityResourcesFormModel.page = page
        this.requestAuthorityResources()
      },
      resourcesPageSizeChange (size) {
        this.searchAuthorityResourcesFormModel.size = size
        this.requestAuthorityResources()
      },
      deleteAuthorityResources (resourceId, index) {
        this.$Modal.confirm({
          title: '删除',
          content: '<h3> 确定需要当前权限下此资源吗？</h3>',
          loading: true,
          onOk: () => {
            this.deleteAuthorityResourcesRequest(resourceId, index)
          }
        })
      },
      deleteAuthorityResourcesRequest (resourceId, index) {
        this.$api.requestUtil('/authority/andresource/' + this.queryAuthorityId + '/' + resourceId, 'delete', '').then(res => {
          console.log('查询结果:')
          console.log(res)
          this.$Modal.remove()
          if (res.responseCode === '00000') {
            this.tableAuthorityResourcesData.splice(index, 1)
            this.resourcesTotalNum = this.resourcesTotalNum - 1
            this.$Message.success('删除成功!')
          }
        }).catch(err => {
          console.error('哎哟~！', err)
        })
      },
      removeAuthority (authorityId, index) {
        this.$Modal.confirm({
          title: '删除',
          content: '<h3> 确定需要删除当前权限吗？</h3>',
          loading: true,
          onOk: () => {
            this.deleteAuthorityRequest(authorityId, index)
          }
        })
      },
      deleteAuthorityRequest (authorityId, index) {
        this.$api.requestUtil('/authority/' + authorityId, 'delete', '').then(res => {
          console.log('查询结果:')
          console.log(res)
          this.$Modal.remove()
          if (res.responseCode === '00000') {
            this.tableAuthorityData.splice(index, 1)
            this.totalNum = this.totalNum - 1
            this.$Message.success('删除成功!')
          }
        }).catch(err => {
          console.error('哎哟~！', err)
        })
      },
      showDetailAuthorityModalBtn (authorityData) {
        if (authorityData === '') {
          this.detailAuthorityFormModel = {}
          this.detailAuthorityFormModel.enable = true
        } else {
          this.detailAuthorityFormModel = authorityData
        }
        this.detailAuthorityModal = true
      },
      addAuthorityResourcesBtn () {
        this.addAuthorityResourcesTableData = []
        this.requestAuthorityResourcesForAdd()
        this.addAuthorityResourcesModal = true
      },
      resetForm (name) {
        this.$refs[name].resetFields()
      },
      requestAuthorityResourcesForAdd () {
        this.addAuthorityResourcesTableLoading = true
        this.$api.requestUtil('/authority/andresource/not/' + this.queryAuthorityId + '?resourceName=' + this.searchAddAuthorityResourcesFormModel.resourceName + '&page=' + this.searchAddAuthorityResourcesFormModel.page + '&size=' + this.searchAddAuthorityResourcesFormModel.size, 'get', '').then(res => {
          console.log('查询结果:')
          console.log(res)
          if (res.responseCode === '00000') {
            this.addAuthorityResourcesTableData = res.data.content
            this.addAuthorityResourcesTotalNum = res.data.totalElements
          }
        }).catch(err => {
          console.error('哎哟~！', err)
        })
        this.addAuthorityResourcesTableLoading = false
      },
      addAuthorityResourcesPageChange (page) {
        // 这里直接更改了模拟的数据，真实使用场景应该从服务端获取数据
        this.searchAddAuthorityResourcesFormModel.page = page
        this.requestAuthorityResourcesForAdd()
      },
      addAuthorityResourcesPageSizeChange (size) {
        this.searchAddAuthorityResourcesFormModel.size = size
        this.requestAuthorityResourcesForAdd()
      },
      addAuthorityResourcesRequestBtn () {
        this.$api.requestUtil('/authority/andresource/' + this.queryAuthorityId, 'post', this.addAuthorityResourcesTableSelectedData).then(res => {
          console.log('查询结果:')
          console.log(res)
          if (res.responseCode === '00000') {
            this.$Message.success('提交成功!')
            this.requestAuthorityResourcesForAdd()
            this.requestAuthorityResources()
          }
        })
      },
      addAuthorityResourcesTableSelectionChange (selection, row) {
        console.log(selection)
        this.addAuthorityResourcesTableSelectedData = selection
      }
    }
  }
</script>
