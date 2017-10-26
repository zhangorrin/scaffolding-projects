<template>
  <div>
    <Form ref="searchRoleForm" :model="searchRoleFormModel" inline>
      <FormItem prop="roleName">
          <Input type="text" v-model="searchRoleFormModel.roleName" placeholder="角色名称">
          </Input>
      </FormItem>
      <FormItem prop="roleDesc">
          <Input type="text" v-model="searchRoleFormModel.roleDesc" placeholder="角色说明">
          </Input>
      </FormItem>
      <FormItem prop="createdDateStart">
          <DatePicker type="date" format="yyyy-MM-dd" v-model="searchRoleFormModel.createdDateStart" placeholder="创建开始日期" style="width: 120px"></DatePicker>
      </FormItem>
      <FormItem prop="createdDateEnd">
          <DatePicker type="date" format="yyyy-MM-dd" v-model="searchRoleFormModel.createdDateEnd" placeholder="创建结束日期" style="width: 120px"></DatePicker>
      </FormItem>
      <FormItem>
          <Button type="primary" icon="ios-search" @click="formHandleSubmit('searchRoleForm')">搜索</Button>
          <Button type="ghost" icon="plus-round" @click="showDetailRoleModalBtn('')">新增</Button>
      </FormItem>
    </Form>

    <Tabs v-model="currentTabsValue" :size="tableSize" >
        <TabPane label="角色" icon="android-list" name="tabPaneRole">
            <Table :data="tableRoleData" border :loading="tableRoleLoading" :size="tableSize"
              :columns="tableRoleColumns" height="435"></Table>
            <div style="margin: 10px;overflow: hidden">
                <div style="float: right;">
                    <Page :size="tableSize" :total="tableRoleTotalNum" :current="tableRoleCurrentPage"
                      @on-change="tableRolePageChange" @on-page-size-change="tableRolePageSizeChange"
                      show-elevator show-sizer show-total></Page>
                </div>
            </div>
        </TabPane>
        <TabPane label="权限信息" icon="connection-bars"  name="tabPaneRoleAuthorities">
            <Form ref="searchRoleAuthoritiesForm" :model="searchRoleAuthoritiesFormModel" style="margin-left: 10px" inline>
              <FormItem prop="authorityMark">
                  <Input type="text" :size="tableSize" v-model="searchRoleAuthoritiesFormModel.authorityMark" placeholder="权限标识"></Input>
              </FormItem>
              <FormItem prop="authorityName">
                  <Input type="text" :size="tableSize" v-model="searchRoleAuthoritiesFormModel.authorityName" placeholder="权限名称"></Input>
              </FormItem>
              <FormItem>
                  <Button type="info" icon="ios-search" :size="tableSize" @click="formHandleSubmit('searchRoleAuthoritiesForm')">搜索权限</Button>
              </FormItem>
              <FormItem>
                  <Button type="primary" icon="ios-plus-outline" :size="tableSize" @click="addRoleAuthoritiesBtn()">添加权限</Button>
              </FormItem>
            </Form>
            <Table :data="tableRoleAuthoritiesData" border :loading="authoritiesLoading" :size="tableSize" :columns="tableRoleAuthoritiesColumns" height="435"></Table>
            <div style="margin: 10px;overflow: hidden">
                <div style="float: right;">
                    <Page :size="tableSize" :total="authoritiesTotalNum" :current="authoritiesCurrentPage" @on-change="authoritiesPageChange" @on-page-size-change="authoritiesPageSizeChange" show-elevator show-sizer show-total></Page>
                </div>
            </div>
        </TabPane>
    </Tabs>


    <Modal v-model="detailRoleModal" width="880" :mask-closable="false" :scrollable="true">
        <p slot="header" style="color:#3399ff;text-align:left">
            <Icon type="icon-addpeople_fill"></Icon>
            <span>角色信息</span>
        </p>

        <Form ref="detailRoleForm" :model="detailRoleFormModel" :rules="detailRoleFormRule" :label-width="75" label-position="right" >

          <Row  style="text-align: right">
              <Col span="12">
                <FormItem label="角色代码：" prop="roleId">
                    <Input type="text" v-model="detailRoleFormModel.roleId" disabled ></Input>
                </FormItem>
              </Col>
              <Col span="12">
                <FormItem label="角色名称:" prop="roleName">
                    <Input type="text" v-model="detailRoleFormModel.roleName" placeholder="请输入权限标识">
                    </Input>
                </FormItem>
              </Col>
          </Row>

          <Row>
            <Col span="12">
              <FormItem label="角色说明:" prop="roleDesc">
                  <Input type="textarea" v-model="detailRoleFormModel.roleDesc" :rows="2" placeholder="请输入提示信息"></Input>
              </FormItem>
            </Col>
            <Col span="6">
                <FormItem label="是否可用:" prop="enable">
                  <i-switch v-model="detailRoleFormModel.enable" size="large">
                      <span slot="open">可用</span>
                      <span slot="close">不可</span>
                  </i-switch>
                </FormItem>
            </Col>
            <Col span="6">
              <FormItem label="系统资源:" prop="issys">
                <i-switch v-model="detailRoleFormModel.issys" size="large">
                    <span slot="open">是</span>
                    <span slot="close">否</span>
                </i-switch>
              </FormItem>
            </Col>
          </Row>
          <Row>
              <Col span="5">
                <FormItem label="创建人：" prop="createdBy" :label-width="70">
                  <Input v-model="detailRoleFormModel.createdBy" disabled ></Input>
                </FormItem>
              </Col>
              <Col span="6">
                <FormItem label="创建时间：" prop="createdDate" :label-width="75">
                  <Input v-model="detailRoleFormModel.createdDate" disabled></Input>
                </FormItem>
              </Col>
              <Col span="6">
                <FormItem label="最后更新人：" prop="lastModifiedBy" :label-width="85">
                  <Input v-model="detailRoleFormModel.lastModifiedBy" disabled></Input>
                </FormItem>
              </Col>
              <Col span="7">
                <FormItem label="最后更新时间：" prop="lastModifiedDate" :label-width="115">
                  <Input v-model="detailRoleFormModel.lastModifiedDate" disabled></Input>
                </FormItem>
              </Col>
          </Row>
          <Row  style="text-align: center">
            <FormItem>
                  <Button type="primary" @click="formHandleSubmit('detailRoleForm')">提交</Button>
                  <Button type="ghost" @click="resetForm('detailRoleForm')" style="margin-left: 38px">重置</Button>
            </FormItem>
          </Row>
        </Form>

        <div slot="footer" style="text-align:right">
        </div>
    </Modal>

    <Modal v-model="addRoleAuthorityModal" width="880" :mask-closable="false" :scrollable="true">
        <p slot="header" style="color:#3399ff;text-align:left">
            <Icon type="icon-addpeople_fill"></Icon>
            <span>角色关联权限</span>
        </p>
        <Form ref="searchAddRoleAuthorityForm" :model="searchAddRoleAuthorityFormModel" inline  label-position="right" >
              <FormItem prop="authorityMark">
                  <Input type="text" :size="tableSize" v-model="searchAddRoleAuthorityFormModel.authorityMark" placeholder="权限标识"></Input>
              </FormItem>
              <FormItem prop="authorityName">
                  <Input type="text" :size="tableSize" v-model="searchAddRoleAuthorityFormModel.authorityName" placeholder="权限名称"></Input>
              </FormItem>
              <FormItem>
                  <Button type="info" icon="ios-search" :size="tableSize" @click="formHandleSubmit('searchAddRoleAuthorityForm')">搜索权限</Button>
              </FormItem>
              <FormItem>
                  <Button type="warning" icon="ios-plus-outline" :size="tableSize" @click="addRoleAuthorityRequestBtn()">添加权限到当前角色</Button>
              </FormItem>
        </Form>
        <Table :data="addRoleAuthoritiesTableData" border :loading="addRoleAuthoritiesTableLoading"
        :size="tableSize" :columns="addRoleAuthoritiesTableColumns" height="435"
        @on-selection-change="addRoleAuthoritiesTableSelectionChange"></Table>
        <div style="margin: 10px;overflow: hidden">
            <div style="float: right;">
                <Page :size="tableSize" :total="addRoleAuthoritiesTotalNum" :current="addRoleAuthoritiesCurrentPage" @on-change="addRoleAuthoritiesPageChange" @on-page-size-change="addRoleAuthoritiesPageSizeChange" show-elevator show-sizer show-total></Page>
            </div>
        </div>
        <div slot="footer" style="text-align:right">
        </div>
    </Modal>
  </div>

</template>
<script>
  import {hierarchicalEdgeBundling} from 'vued3tree'
  import { getRoleStringRegex } from '@/common/utils'

  export default {
    name: 'roleslist',
    components: {
      hierarchicalEdgeBundling
    },
    data () {
      return {
        addRoleAuthoritiesTableSelectedData: [],
        addRoleAuthoritiesTableData: [],
        addRoleAuthoritiesTableLoading: false,
        addRoleAuthoritiesTableColumns: [
          { // @:columns
            type: 'selection', // 开启checkbox
            width: 55,
            fixed: 'left',
            key: 'selectResource',
            align: 'center'
          },
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
          }
        ],
        addRoleAuthoritiesTotalNum: 0,
        addRoleAuthoritiesCurrentPage: 1,
        searchAddRoleAuthorityFormModel: {
          authorityMark: '',
          authorityName: '',
          page: 0,
          size: 10
        },
        addRoleAuthorityModal: false,
        tableRoleAuthoritiesData: [],
        authoritiesLoading: false,
        tableRoleAuthoritiesColumns: [
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
            width: 70,
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
                      this.deleteRoleAuthorities(params.row.authorityId, params.index)
                    }
                  }
                }, '删除')
              ])
            }
          }
        ],
        authoritiesTotalNum: 0,
        authoritiesCurrentPage: 1,
        searchRoleAuthoritiesFormModel: {
          authorityMark: '',
          authorityName: '',
          page: 0,
          size: 10
        },
        detailRoleFormRule: {
          roleName: [
            { required: true, type: 'string', message: '角色名称必须以 "ROLE_" 开头', trigger: 'blur', pattern: getRoleStringRegex() }
          ],
          roleDesc: [
            { required: true, message: '角色说明不能为空', trigger: 'blur' }
          ],
          enable: [
            { type: 'boolean', required: true, message: '请选择是否可用', trigger: 'blur' }
          ]
        },
        detailRoleFormModel: {
          createdBy: '',
          createdDate: '',
          lastModifiedBy: '',
          lastModifiedDate: '',
          roleId: '',
          roleName: '',
          roleDesc: '',
          enable: true,
          issys: true,
          moduleId: ''
        },
        detailRoleModal: false,
        tableRoleTotalNum: 0,
        tableRoleCurrentPage: 1,
        tableRoleColumns: [
          {
            title: '角色名称',
            key: 'roleName',
            align: 'left',
            width: 250,
            fixed: 'left',
            sortable: true
          },
          {
            title: '角色代码',
            align: 'left',
            width: 250,
            key: 'roleId'
          },
          {
            title: '角色说明',
            align: 'left',
            width: 250,
            key: 'roleDesc'
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
                      this.showDetailRoleModalBtn(params.row)
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
                      this.showTabPaneRoleAuthorities(params.row.roleId)
                    }
                  }
                }, '权限信息'),
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
                      this.removeRole(params.row.roleId, params.index)
                    }
                  }
                }, '删除')
              ])
            }
          }
        ],
        tableRoleData: [],
        tableRoleLoading: false,
        tableSize: 'small',
        currentTabsValue: 'tabPaneRole',
        searchRoleFormModel: {
          roleName: '',
          roleDesc: '',
          createdDateStart: '',
          createdDateEnd: '',
          page: 0,
          size: 10
        },
        queryRoleId: ''
      }
    },
    mounted: function () {
      this.queryRoleForm()
    },
    methods: {
      queryRoleForm () {
        this.formHandleSubmit('searchRoleForm')
      },
      formHandleSubmit (name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            if (name === 'searchRoleForm') {
              this.tableRoleLoading = true
              this.$api.requestUtil('/role/list', 'post', this.searchRoleFormModel).then(res => {
                console.log('查询结果:')
                console.log(res)
                if (res.responseCode === '00000') {
                  this.tableRoleData = res.data.content
                  this.tableRoleTotalNum = res.data.totalElements
                }
              })
              this.tableRoleLoading = false
            } else if (name === 'detailRoleForm') {
              this.$api.requestUtil('/role', 'post', this.detailRoleFormModel).then(res => {
                console.log('查询结果:')
                console.log(res)
                if (res.responseCode === '00000') {
                  this.detailRoleFormModel = res.data
                  this.queryRoleForm()
                  this.$Message.success('提交成功!')
                }
              })
            } else if (name === 'searchRoleAuthoritiesForm') {
              this.authoritiesLoading = true
              this.$api.requestUtil('/role/andauthorities/' + this.queryRoleId + '?authorityMark=' + this.searchRoleAuthoritiesFormModel.authorityMark + '&authorityName=' + this.searchRoleAuthoritiesFormModel.authorityName + '&size=' + this.searchRoleAuthoritiesFormModel.size + '&page=' + this.searchRoleAuthoritiesFormModel.page, 'get', '').then(res => {
                console.log('查询结果:')
                console.log(res)
                if (res.responseCode === '00000') {
                  this.tableRoleAuthoritiesData = res.data.authorities.content
                  this.authoritiesTotalNum = res.data.authorities.totalElements
                }
              })
              this.authoritiesLoading = false
            } else if (name === 'searchAddRoleAuthorityForm') {
              this.addRoleAuthoritiesTableLoading = true
              this.$api.requestUtil('/role/andauthorities/not/' + this.queryRoleId + '?authorityMark=' + this.searchAddRoleAuthorityFormModel.authorityMark + '&authorityName=' + this.searchAddRoleAuthorityFormModel.authorityName + '&size=' + this.searchAddRoleAuthorityFormModel.size + '&page=' + this.searchAddRoleAuthorityFormModel.page, 'get', '').then(res => {
                console.log('查询结果:')
                console.log(res)
                if (res.responseCode === '00000') {
                  this.addRoleAuthoritiesTableData = res.data.content
                  this.addRoleAuthoritiesTotalNum = res.data.totalElements
                }
              })
              this.addRoleAuthoritiesTableLoading = false
            }
          } else {
            this.$Message.error('表单验证失败!')
          }
        })
      },
      resetForm (name) {
        this.$refs[name].resetFields()
      },
      tableRolePageChange (page) {
        // 这里直接更改了模拟的数据，真实使用场景应该从服务端获取数据
        this.searchRoleFormModel.page = page
        this.formHandleSubmit('searchRoleForm')
      },
      tableRolePageSizeChange (size) {
        this.searchRoleFormModel.size = size
        this.formHandleSubmit('searchRoleForm')
      },
      showDetailRoleModalBtn (data) {
        if (data === '') {
          this.detailRoleFormModel = {}
          this.detailRoleFormModel.enable = true
        } else {
          this.detailRoleFormModel = data
        }
        this.detailRoleModal = true
      },
      showTabPaneRoleAuthorities (queryRoleId) {
        this.queryRoleId = queryRoleId
        this.currentTabsValue = 'tabPaneRoleAuthorities'
        this.formHandleSubmit('searchRoleAuthoritiesForm')
      },
      removeRole (roleId, index) {
        this.$Modal.confirm({
          title: '删除',
          content: '<h3> 确定需要删除当前角色吗？</h3>',
          loading: true,
          onOk: () => {
            this.deleteRoleRequest(roleId, index)
          }
        })
      },
      deleteRoleRequest (roleId, index) {
        this.$api.requestUtil('/role/' + roleId, 'delete', '').then(res => {
          this.$Modal.remove()
          if (res.responseCode === '00000') {
            this.tableRoleData.splice(index, 1)
            this.tableRoleTotalNum = this.tableRoleTotalNum - 1
            this.$Message.success('删除成功!')
          }
        })
      },
      authoritiesPageChange (page) {
        // 这里直接更改了模拟的数据，真实使用场景应该从服务端获取数据
        this.searchRoleAuthoritiesFormModel.page = page
        this.formHandleSubmit('searchRoleAuthoritiesForm')
      },
      authoritiesPageSizeChange (size) {
        this.searchRoleAuthoritiesFormModel.size = size
        this.formHandleSubmit('searchRoleAuthoritiesForm')
      },
      deleteRoleAuthorities (authorityId, index) {
        this.$Modal.confirm({
          title: '删除',
          content: '<h3> 确定需要当前角色下此权限吗？</h3>',
          loading: true,
          onOk: () => {
            this.deleteRoleAuthoritiesRequest(authorityId, index)
          }
        })
      },
      deleteRoleAuthoritiesRequest (authorityId, index) {
        this.$api.requestUtil('/role/andauthorities/' + this.queryRoleId + '/' + authorityId, 'delete', '').then(res => {
          console.log('删除结果:')
          console.log(res)
          this.$Modal.remove()
          if (res.responseCode === '00000') {
            this.tableRoleAuthoritiesData.splice(index, 1)
            this.authoritiesTotalNum = this.authoritiesTotalNum - 1
            this.$Message.success('删除成功!')
          }
        }).catch(err => {
          console.error('哎哟~！', err)
        })
      },
      addRoleAuthoritiesBtn () {
        this.addRoleAuthorityModal = true
        this.formHandleSubmit('searchAddRoleAuthorityForm')
      },
      addRoleAuthoritiesTableSelectionChange (selection) {
        console.log(selection)
        this.addRoleAuthoritiesTableSelectedData = selection
      },
      addRoleAuthoritiesPageChange (page) {
        // 这里直接更改了模拟的数据，真实使用场景应该从服务端获取数据
        this.searchAddRoleAuthorityFormModel.page = page
        this.formHandleSubmit('searchAddRoleAuthorityForm')
      },
      addRoleAuthoritiesPageSizeChange (size) {
        this.searchAddRoleAuthorityFormModel.size = size
        this.formHandleSubmit('searchAddRoleAuthorityForm')
      },
      addRoleAuthorityRequestBtn () {
        this.$api.requestUtil('/role/andauthorities/' + this.queryRoleId, 'post', this.addRoleAuthoritiesTableSelectedData).then(res => {
          console.log('查询结果:')
          console.log(res)
          if (res.responseCode === '00000') {
            this.formHandleSubmit('searchAddRoleAuthorityForm')
            this.formHandleSubmit('searchRoleAuthoritiesForm')
            this.$Message.success('提交成功!')
          }
        })
      }
    }
  }
</script>
