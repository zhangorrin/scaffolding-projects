f<template>
    <div>
        <Form ref="searchForm" :model="searchForm" inline>
          <FormItem prop="userName">
              <Input type="text" v-model="searchForm.userName" placeholder="用户名">
              </Input>
          </FormItem>
          <FormItem prop="name">
              <Input type="text" v-model="searchForm.name" placeholder="姓名">
              </Input>
          </FormItem>
          <FormItem prop="mobile">
              <Input type="text" v-model="searchForm.mobile" placeholder="手机号码">
              </Input>
          </FormItem>
          <FormItem prop="createdDateStart">
              <DatePicker type="date" format="yyyy-MM-dd" v-model="searchForm.createdDateStart" placeholder="注册开始日期" style="width: 120px"></DatePicker>
          </FormItem>
          <FormItem prop="createdDateEnd">
              <DatePicker type="date" format="yyyy-MM-dd" v-model="searchForm.createdDateEnd" placeholder="注册结束日期" style="width: 120px"></DatePicker>
          </FormItem>
          <FormItem>
              <Button type="primary" icon="ios-search" @click="handleSubmit('searchForm')">搜索</Button>
              <Button @click="openDetailModalShow(true, detailUserFormItemInit)" icon="person-add">新增用户</Button>
          </FormItem>
      </Form>
      <Tabs v-model="currentTabsValue" :size="tableSize" >
          <TabPane label="用户" icon="android-list" name="tabPaneUser">
              <Table :data="tableData1" border :loading="loading" :size="tableSize" :columns="tableColumns1" height="435"></Table>
              <div style="margin: 10px;overflow: hidden">
                  <div style="float: right;">
                      <Page :size="tableSize" :total="totalNum" :current="currentPage" @on-change="pageChange" @on-page-size-change="pageSizeChange" show-elevator show-sizer show-total></Page>
                  </div>
              </div>
          </TabPane>
          <TabPane label="角色信息" icon="connection-bars"  name="tabPaneUserRoles">
              <Form ref="searchRoleForm" :model="searchRoleFormModel" inline>
                <FormItem prop="roleName">
                    <Input type="text" v-model="searchRoleFormModel.roleName" placeholder="角色名称">
                    </Input>
                </FormItem>
                <FormItem prop="roleDesc">
                    <Input type="text" v-model="searchRoleFormModel.roleDesc" placeholder="角色说明">
                    </Input>
                </FormItem>
                <FormItem>
                    <Button type="primary" :size="tableSize" icon="ios-search" @click="formHandleSubmit('searchRoleForm')">搜索</Button>
                    <Button type="ghost" :size="tableSize" icon="plus-round" @click="addUserRolesBtn()">新增角色</Button>
                </FormItem>
              </Form>
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
      </Tabs>


      <Modal v-model="detailModalShow" width="880" :mask-closable="false" :scrollable="true">
          <p slot="header" style="color:#3399ff;text-align:left">
              <Icon type="icon-addpeople_fill"></Icon>
              <span>{{modalTitle}}  {{detailUserFormItem.userId}}</span>
          </p>
          <Form ref="detailUserFormItem" :model="detailUserFormItem" :rules="detailUserFormValidate" :label-width="75" label-position="right">
            <Row  style="text-align: right">

                <Col span="6">
                  <FormItem label="用户代码：" prop="userId">
                      <Input type="text" v-model="detailUserFormItem.userId" disabled ></Input>
                  </FormItem>
                </Col>

                <Col span="6">
                  <FormItem label="用户名：" prop="username">
                      <Input v-model="detailUserFormItem.username" placeholder="请输入用户名"></Input>
                  </FormItem>
                </Col>

                <Col span="6">
                  <FormItem label="手机号码：" prop="mobile" :label-width="85">
                    <Input v-model="detailUserFormItem.mobile" placeholder="请输入手机号码"></Input>
                  </FormItem>
                </Col>

                <Col span="6">
                  <FormItem label="姓名：" prop="name">
                      <Input v-model="detailUserFormItem.name" placeholder="请输入真是姓名"></Input>
                  </FormItem>
                </Col>
            </Row>

            <Row>
              <Col span="6">
                  <FormItem label="是否可用：" prop="enabled">
                  <i-switch v-model="detailUserFormItem.enabled" size="large">
                      <span slot="open">可用</span>
                      <span slot="close">不可</span>
                  </i-switch>
                  </FormItem>
              </Col>
              <Col span="6">
                <FormItem label="是否锁定：" prop="accountNonLocked">
                <i-switch v-model="detailUserFormItem.accountNonLocked" size="large">
                    <span slot="open">未锁</span>
                    <span slot="close">已锁</span>
                </i-switch>
                </FormItem>
              </Col>
              <Col span="6">
                  <FormItem label="账号是否过期：" prop="accountNonExpired" :label-width="115">
                  <i-switch v-model="detailUserFormItem.accountNonExpired" size="large">
                      <span slot="open">未过</span>
                      <span slot="close">已过</span>
                  </i-switch>
                  </FormItem>
              </Col>
              <Col span="6">
                  <FormItem label="证书是否有效：" prop="credentialsNonExpired" :label-width="115">
                  <i-switch v-model="detailUserFormItem.credentialsNonExpired" size="large">
                      <span slot="open">有效</span>
                      <span slot="close">无效</span>
                  </i-switch>
                  </FormItem>
              </Col>
            </Row>

            <Row>
                <Col span="8">
                    <FormItem label="机构编号：" prop="vqzjgid">
                      <Input v-model="detailUserFormItem.vqzjgid" disabled ></Input>
                    </FormItem>
                </Col>
                <Col span="16">
                    <FormItem label="机构名称：" prop="vqzjgmc"  :label-width="150">
                      <Input v-model="detailUserFormItem.vqzjgmc" disabled></Input>
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="8">
                    <FormItem label="地区编号：" prop="depId">
                    <Input v-model="detailUserFormItem.depId" disabled ></Input>
                  </FormItem>
                </Col>
                <Col span="16">
                    <FormItem label="地区名称：" prop="depName" :label-width="150">
                    <Input v-model="detailUserFormItem.depName" disabled></Input>
                  </FormItem>
                </Col>
            </Row>

              <Row>
                  <Col span="8">
                    <FormItem label="最后登录：" prop="lastLogin">
                      <Input v-model="detailUserFormItem.lastLogin" disabled ></Input>
                    </FormItem>
                  </Col>
                  <Col span="16">
                    <FormItem label="最后登录ip地址：" prop="loginIp" :label-width="150">
                      <Input v-model="detailUserFormItem.loginIp" disabled></Input>
                    </FormItem>
                  </Col>
              </Row>
              <Row>
                  <Col span="5">
                    <FormItem label="创建人：" prop="createdBy" :label-width="70">
                      <Input v-model="detailUserFormItem.createdBy" disabled ></Input>
                    </FormItem>
                  </Col>
                  <Col span="6">
                    <FormItem label="创建时间：" prop="createdDate" :label-width="75">
                      <Input v-model="detailUserFormItem.createdDate" disabled></Input>
                    </FormItem>
                  </Col>
                  <Col span="6">
                    <FormItem label="最后更新人：" prop="lastModifiedBy" :label-width="85">
                      <Input v-model="detailUserFormItem.lastModifiedBy" disabled></Input>
                    </FormItem>
                  </Col>
                  <Col span="7">
                    <FormItem label="最后更新时间：" prop="lastModifiedDate" :label-width="115">
                      <Input v-model="detailUserFormItem.lastModifiedDate" disabled></Input>
                    </FormItem>
                  </Col>
              </Row>
            </FormItem>

            <Form-item style="text-align:center;margin-left: -58px">
              <Button type="primary" @click="detailSubmit('detailUserFormItem')">提交</Button>
              <Button type="ghost" @click="detailReset('detailUserFormItem')" style="margin-left: 38px">重置</Button>
            </Form-item>

          </Form>


          <div slot="footer" style="text-align:right">
          </div>
      </Modal>
      <Modal v-model="updatePwdModal" width="380" :mask-closable="false" :scrollable="true" >
          <p slot="header" style="color:#3399ff;text-align:left">
              <Icon type="icon-addpeople_fill"></Icon>
              <span>修改密码</span>
          </p>

          <Form ref="updatePwdForm" :model="updatePwdForm" :rules="updatePwdFormRule" :label-width="60">
              <FormItem label="用户代码" prop="userId">
                  <Input type="text" v-model="updatePwdForm.userId" disabled></Input>
              </FormItem>
              <FormItem label="密码" prop="passwd">
                  <Input type="password" placeholder="请输入新密码" v-model="updatePwdForm.passwd"></Input>
              </FormItem>
              <FormItem label="确认密码" prop="passwdCheck">
                  <Input type="password" placeholder="请再次输入新密码" v-model="updatePwdForm.passwdCheck"></Input>
              </FormItem>
              <FormItem>
                  <Button type="primary" @click="updatePwdFormSubmit('updatePwdForm')">提交</Button>
              </FormItem>
          </Form>
          <div slot="footer" style="text-align:right">
          </div>
      </Modal>

      <Modal v-model="addUserRolesModal" width="880" :mask-closable="false" :scrollable="true">
          <p slot="header" style="color:#3399ff;text-align:left">
              <Icon type="icon-addpeople_fill"></Icon>
              <span>用户关联角色</span>
          </p>
          <Form ref="searchAddUserRolesForm" :model="searchAddUserRolesFormModel" inline  label-position="right" >
                <FormItem prop="roleName">
                    <Input type="text" v-model="searchAddUserRolesFormModel.roleName" placeholder="角色名称">
                    </Input>
                </FormItem>
                <FormItem prop="roleDesc">
                    <Input type="text" v-model="searchAddUserRolesFormModel.roleDesc" placeholder="角色说明">
                    </Input>
                </FormItem>
                <FormItem>
                    <Button type="info" icon="ios-search" :size="tableSize" @click="formHandleSubmit('searchAddUserRolesForm')">搜索权限</Button>
                </FormItem>
                <FormItem>
                    <Button type="warning" icon="ios-plus-outline" :size="tableSize" @click="addUserRolesRequestBtn()">添加角色到当前用户</Button>
                </FormItem>
          </Form>
          <Table :data="addUserRolesTableData" border :loading="addUserRolesTableLoading"
          :size="tableSize" :columns="addUserRolesTableColumns" height="435"
          @on-selection-change="addUserRolesTableSelectionChange"></Table>
          <div style="margin: 10px;overflow: hidden">
              <div style="float: right;">
                  <Page :size="tableSize" :total="addUserRolesTotalNum" :current="addUserRolesCurrentPage" @on-change="addUserRolesPageChange" @on-page-size-change="addUserRolesPageSizeChange" show-elevator show-sizer show-total></Page>
              </div>
          </div>
          <div slot="footer" style="text-align:right">
          </div>
      </Modal>

    </div>
</template>
<script>
    import { getMobileRegex } from '@/common/utils'

    export default {
      data () {
        const validatePass = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请输入密码'))
          } else if (value !== '' && value.length < 8) {
            callback(new Error('密码至少为8位'))
          } else {
            if (this.updatePwdForm.passwdCheck !== '') {
              // 对第二个密码框单独验证
              this.$refs.updatePwdForm.validateField('passwdCheck')
            }
            callback()
          }
        }
        const validatePassCheck = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请再次输入密码'))
          } else if (value !== this.updatePwdForm.passwd) {
            callback(new Error('两次输入密码不一致!'))
          } else if (value !== '' && value.length < 8) {
            callback(new Error('密码至少为8位'))
          } else {
            callback()
          }
        }
        return {
          addUserRolesTableData: [],
          addUserRolesTableLoading: false,
          addUserRolesTableColumns: [
            { // @:columns
              type: 'selection', // 开启checkbox
              width: 55,
              fixed: 'left',
              key: 'selectResource',
              align: 'center'
            },
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
            }
          ],
          addUserRolesTotalNum: 0,
          addUserRolesCurrentPage: 1,
          searchAddUserRolesFormModel: {
            roleName: '',
            roleDesc: '',
            page: 0,
            size: 10
          },
          addUserRolesModal: false,
          tableRoleDataSelectedData: [],
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
                        this.removeRole(params.row.roleId, params.index)
                      }
                    }
                  }, '删除')
                ])
              }
            }
          ],
          tableRoleTotalNum: 0,
          tableRoleCurrentPage: 1,
          tableRoleLoading: false,
          tableRoleData: [],
          searchRoleFormModel: {
            roleName: '',
            roleDesc: '',
            page: 0,
            size: 10
          },
          queryUserId: '',
          currentTabsValue: 'tabPaneUser',
          updatePwdModal: false,
          updatePwdForm: {
            userId: '',
            passwd: '',
            passwdCheck: ''
          },
          updatePwdFormRule: {
            passwd: [
              { validator: validatePass, trigger: 'blur' }
            ],
            passwdCheck: [
              { validator: validatePassCheck, trigger: 'blur' }
            ]
          },
          detailUserFormItemInit: {
            userId: '',
            username: '',
            mobile: '',
            name: '',
            enabled: true,
            accountNonLocked: true,
            accountNonExpired: true,
            credentialsNonExpired: true,
            createdBy: '',
            createdDate: '',
            lastModifiedBy: '',
            lastModifiedDate: '',
            lastLogin: '',
            loginIp: '',
            vqzjgid: '',
            vqzjgmc: '',
            depId: '',
            depName: ''
          },
          detailUserFormItem: {
            userId: '',
            username: '',
            mobile: '',
            name: '',
            enabled: true,
            accountNonLocked: true,
            accountNonExpired: true,
            credentialsNonExpired: true,
            createdBy: '',
            createdDate: '',
            lastModifiedBy: '',
            lastModifiedDate: '',
            lastLogin: '',
            loginIp: '',
            vqzjgid: '',
            vqzjgmc: '',
            depId: '',
            depName: ''
          },
          loading: false,
          modalTitle: '详情',
          detailModalShow: false,
          totalNum: 0,
          currentPage: 1,
          total: 100,
          tableSize: 'small',
          tableData1: this.mockTableData1({page: 0, size: 10}),
          tableColumns1: [
            {
              title: '用户名',
              key: 'username',
              align: 'left',
              width: 180,
              fixed: 'left',
              sortable: true
            },
            {
              title: '用户代码',
              key: 'userId',
              width: 260,
              align: 'left'
            },
            {
              title: '手机号',
              align: 'left',
              width: 125,
              key: 'mobile'
            },
            {
              title: '真实名称',
              align: 'left',
              width: 165,
              key: 'name'
            },
            {
              title: '是否可用',
              align: 'left',
              width: 95,
              key: 'enabled',
              render: (h, params) => {
                const row = params.row
                const color = row.enabled === true ? 'green' : 'red'
                const type = row.accountNonLocked === true ? 'checkmark-circled' : 'close-circled'
                // const text = row.enabled === true ? '可用' : '不可用'

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
              title: '是否锁定',
              align: 'left',
              width: 95,
              key: 'accountNonLocked',
              render: (h, params) => {
                const row = params.row
                const color = row.accountNonLocked === true ? 'green' : 'red'
                const type = row.accountNonLocked === true ? 'checkmark-circled' : 'close-circled'
                // const text = row.accountNonLocked === true ? '可用' : '不可用'
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
              title: '注册时间',
              align: 'left',
              width: 155,
              key: 'createdDate'
            },
            {
              title: '部门名称',
              align: 'left',
              width: 225,
              key: 'vqzjgmc'
            },
            {
              title: '操作',
              key: 'action',
              width: 235,
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
                        this.edit(params.row.userId)
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
                        this.updatePwdForm.userId = params.row.userId
                        this.updatePwdModal = true
                      }
                    }
                  }, '重置密码'),
                  h('Button', {
                    props: {
                      type: 'ghost',
                      size: this.tableSize
                    },
                    style: {
                      color: '#f9c397'
                    },
                    on: {
                      click: () => {
                        this.queryUserId = params.row.userId
                        this.currentTabsValue = 'tabPaneUserRoles'
                        this.formHandleSubmit('searchRoleForm')
                      }
                    }
                  }, '角色'),
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
                        this.removeUser(params.row.userId, params.row.username, params.index)
                      }
                    }
                  }, '删除')
                ])
              }
            }
          ],
          searchForm: {
            userName: '',
            name: '',
            mobile: '',
            createdDateStart: '',
            createdDateEnd: '',
            page: 0,
            size: 10
          },
          detailUserFormValidate: {
            username: [
              { required: true, message: '用户名不能为空', trigger: 'blur' }
            ],
            mobile: [
              { required: true, type: 'string', message: '手机号格式不正确', trigger: 'blur', pattern: getMobileRegex() }
            ]
          }
        }
      },
      methods: {
        mockTableData1 (params) {
          let datas = []
          this.loading = true
          this.$api.requestUtil('/user/list', 'post', params).then(res => {
            console.log('查询参数：')
            console.log(params)
            if (res.responseCode === '00000') {
              datas = res.data.content
              this.tableData1 = res.data.content
              this.totalNum = res.data.totalElements
            } else {
              this.$Message.error(JSON.stringify(res))
            }
          }).catch(err => {
            this.loading = false
            console.error('哎哟~！', err)
          })
          this.loading = false
          return datas
        },
        formatDate (date) {
          const y = date.getFullYear()
          let m = date.getMonth() + 1
          m = m < 10 ? '0' + m : m
          let d = date.getDate()
          d = d < 10 ? ('0' + d) : d
          return y + '-' + m + '-' + d
        },
        pageChange (page) {
          // 这里直接更改了模拟的数据，真实使用场景应该从服务端获取数据
          this.searchForm.page = page
          this.tableData1 = this.mockTableData1(this.searchForm)
        },
        pageSizeChange (size) {
          this.searchForm.size = size
          this.tableData1 = this.mockTableData1(this.searchForm)
        },
        handleSubmit (name) {
          this.mockTableData1(this.searchForm)
        },
        /**
         * 表格对应操作方法
         * @remove 删除
         * @edit
         */
        remove (userId, username, index) {
          this.$Modal.confirm({
            title: '删除用户',
            content: '<h3> 确定需要删除  ' + username + '  吗？</h3>',
            loading: true,
            onOk: () => {
              this.requestDelete(userId, index)
            }
          })
        },
        requestDelete (userId, index) {
          this.$api.requestUtil('/user/' + userId, 'delete', '').then(res => {
            if (res.responseCode === '00000') {
              this.$Modal.remove()
              this.tableData1.splice(index, 1)
              this.$Message.success('删除成功!')
            } else {
              this.$Message.error(JSON.stringify(res))
            }
          }).catch(err => {
            this.loading = false
            console.error('哎哟~！', err)
          })
        },
        edit (userId) {
          this.$api.requestUtil('/user/' + userId, 'get', '').then(res => {
            if (res.responseCode === '00000') {
              this.openDetailModalShow(true, res.data)
            } else {
              this.$Message.error(JSON.stringify(res))
            }
          }).catch(err => {
            this.loading = false
            console.error('哎哟~！', err)
          })
        },
        detailSubmit (name) {
          this.$refs[name].validate((valid) => {
            if (valid) {
              this.$api.requestUtil('/user', 'post', this.detailUserFormItem).then(res => {
                if (res.responseCode === '00000') {
                  this.openDetailModalShow(true, res.data)
                  this.$Message.success('提交成功!')
                } else {
                  this.$Message.error(JSON.stringify(res))
                }
              }).catch(err => {
                this.loading = false
                console.error('哎哟~！', err)
              })
            } else {
              this.$Message.error('表单验证失败!')
            }
          })
        },
        detailReset (name) {
          this.$refs[name].resetFields()
        },
        openDetailModalShow (isshow, context) {
          this.detailUserFormItem = context
          this.detailModalShow = isshow
        },
        updatePwdFormSubmit (name) {
          this.$refs[name].validate((valid) => {
            if (valid) {
              this.$api.requestUtil('/user/updatepwd', 'post', this.updatePwdForm).then(res => {
                if (res.responseCode === '00000') {
                  this.$Modal.remove()
                  this.$Message.success('提交成功!')
                } else {
                  this.$Message.error(JSON.stringify(res))
                }
              }).catch(err => {
                this.loading = false
                console.error('哎哟~！', err)
              })
            } else {
              this.$Message.error('表单验证失败!')
            }
          })
        },
        formHandleSubmit (name) {
          this.$refs[name].validate((valid) => {
            if (valid) {
              if (name === 'searchRoleForm') {
                this.tableRoleLoading = true
                this.$api.requestUtil('/user/' + this.queryUserId + '/roles?roleName=' + this.searchRoleFormModel.roleName + '&roleDesc=' + this.searchRoleFormModel.roleDesc + '&page=' + this.searchRoleFormModel.page + '&size=' + this.searchRoleFormModel.size, 'get', '').then(res => {
                  console.log('查询结果:')
                  console.log(res)
                  if (res.responseCode === '00000') {
                    this.tableRoleData = res.data.content
                    this.tableRoleTotalNum = res.data.totalElements
                  }
                })
                this.tableRoleLoading = false
              } else if (name === 'searchAddUserRolesForm') {
                this.addUserRolesTableLoading = true
                this.$api.requestUtil('/user/' + this.queryUserId + '/roles/not?roleName=' + this.searchAddUserRolesFormModel.roleName + '&roleDesc=' + this.searchAddUserRolesFormModel.roleDesc + '&page=' + this.searchAddUserRolesFormModel.page + '&size=' + this.searchAddUserRolesFormModel.size, 'get', '').then(res => {
                  console.log('查询结果:')
                  console.log(res)
                  if (res.responseCode === '00000') {
                    this.addUserRolesTableData = res.data.content
                    this.addUserRolesTotalNum = res.data.totalElements
                  }
                })
                this.addUserRolesTableLoading = false
              }
            } else {
              this.$Message.error('表单验证失败!')
            }
          })
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
          this.$api.requestUtil('/user/' + this.queryUserId + '/role/' + roleId, 'delete', '').then(res => {
            this.$Modal.remove()
            if (res.responseCode === '00000') {
              this.tableRoleData.splice(index, 1)
              this.tableRoleTotalNum = this.tableRoleTotalNum - 1
              this.$Message.success('删除成功!')
            }
          })
        },
        addUserRolesTableSelectionChange (selection) {
          console.log(selection)
          this.tableRoleDataSelectedData = selection
        },
        addUserRolesBtn () {
          this.addUserRolesModal = true
          this.formHandleSubmit('searchAddUserRolesForm')
        },
        addUserRolesPageChange (page) {
          // 这里直接更改了模拟的数据，真实使用场景应该从服务端获取数据
          this.searchAddUserRolesFormModel.page = page
          this.formHandleSubmit('searchAddUserRolesForm')
        },
        addUserRolesPageSizeChange (size) {
          this.searchAddUserRolesFormModel.size = size
          this.formHandleSubmit('searchAddUserRolesForm')
        },
        addUserRolesRequestBtn () {
          this.$api.requestUtil('/user/' + this.queryUserId + '/roles', 'post', this.tableRoleDataSelectedData).then(res => {
            console.log('查询结果:')
            console.log(res)
            if (res.responseCode === '00000') {
              this.formHandleSubmit('searchAddUserRolesForm')
              this.formHandleSubmit('searchRoleForm')
              this.$Message.success('提交成功!')
            }
          })
        },
        removeUser (userId, userName, index) {
          this.$Modal.confirm({
            title: '删除',
            content: '<h3> 确定需要删除 ' + userName + ' 吗？<br></h3>',
            loading: true,
            onOk: () => {
              this.deleteUserRequest(userId, index)
            }
          })
        },
        deleteUserRequest (userId, index) {
          this.$api.requestUtil('/user/' + userId, 'delete', '').then(res => {
            this.$Modal.remove()
            if (res.responseCode === '00000') {
              this.tableData1.splice(index, 1)
              this.totalNum = this.totalNum - 1
              this.$Message.success('删除成功!')
            }
          })
        }
      }
    }
</script>
