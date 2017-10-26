<template>
    <div>
        <Row>
            <Col span="7" style="margin: 10px">
                <Card>
                <Tree ref="resourceTree" :data="resourceTreeData" @on-select-change="resourceSelectChange"></Tree>
                </Card>
            </Col>
            <Col span="16">
              <Affix>
                  <Dropdown>
                      <Button type="primary" icon="ios-plus-outline">
                          新增菜单
                          <Icon type="arrow-down-b"></Icon>
                      </Button>
                      <DropdownMenu slot="list" >
                          <DropdownItem>
                            <Button type="text" size="small" @click="addMainResourceButton('root')">根菜单</Button>
                          </DropdownItem>
                          <DropdownItem>
                            <Button type="text" size="small" @click="addMainResourceButton('abc')">子菜单</Button>
                          </DropdownItem>
                      </DropdownMenu>
                  </Dropdown>

                  <Button type="error" icon="ios-trash" @click="delMainResourceButton()">删除菜单</Button>
                  <Button type="info" icon="ios-plus-outline" @click="addContentResourcesButton()">新增页面资源</Button>
              </Affix>
              <Collapse value="mainResourcePanel">
                <Panel name="mainResourcePanel">
                  &nbsp;&nbsp;菜单详细信息
                  <p slot="content">
                    <Form ref="mainResourceForm" :model="mainResourceFormModel" :rules="resourcesFormRule" :label-width="75" label-position="right">
                        <Row  style="text-align: right">
                            <Col span="8">
                              <FormItem label="资源代码:" prop="resourceId">
                                  <Input type="text" v-model="mainResourceFormModel.resourceId" disabled  ></Input>
                              </FormItem>
                            </Col>

                            <Col span="8">
                              <FormItem label="唯一标识:" prop="globalUniqueId" :label-width="75">
                                  <Input v-model="mainResourceFormModel.globalUniqueId" placeholder="请输入唯一标识码"></Input>
                              </FormItem>
                            </Col>

                            <Col span="8">
                              <FormItem label="资源名称:" prop="resourceName">
                                <Input v-model="mainResourceFormModel.resourceName" placeholder="请输入资源名称"></Input>
                              </FormItem>
                            </Col>
                        </Row>

                        <Row  style="text-align: right">
                            <Col span="8">
                              <FormItem label="父资源代码:" prop="fatherResourceId" :label-width="86">
                                  <Input v-model="mainResourceFormModel.fatherResourceId" disabled></Input>
                              </FormItem>
                            </Col>
                            <Col span="6">
                              <FormItem label="资源类型:" prop="resourceType">
                                  <Select v-model="mainResourceFormModel.resourceType" placeholder="请选择" style="text-align: center">
                                    <Option v-for="item in resourceTypeList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                                  </Select>
                              </FormItem>
                            </Col>
                            <Col span="6">
                              <FormItem label="请求方法:" prop="requestMethod">
                                  <Select v-model="mainResourceFormModel.requestMethod" placeholder="请选择" style="text-align: center">
                                    <Option v-for="item in requestMethodList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                                  </Select>
                              </FormItem>
                            </Col>

                            <Col span="4">
                                <FormItem label="是否可用:" prop="enable">
                                  <i-switch v-model="mainResourceFormModel.enable" size="large">
                                      <span slot="open">可用</span>
                                      <span slot="close">不可</span>
                                  </i-switch>
                                </FormItem>
                            </Col>
                        </Row>

                        <Row  style="text-align: right">
                            <Col span="16">
                              <FormItem label="资源路径:" prop="resourcePath">
                                  <Input v-model="mainResourceFormModel.resourcePath"></Input>
                              </FormItem>
                            </Col>
                            <Col span="8">
                                <FormItem label="图标:" prop="icon">
                                  <Input v-model="mainResourceFormModel.icon"></Input>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row  style="text-align: right">
                          <Col span="14">
                              <FormItem label="资源描述" prop="resourceDesc">
                                  <Input type="textarea" v-model="mainResourceFormModel.resourceDesc" :rows="2" placeholder="请输入..."></Input>
                              </FormItem>
                          </Col>
                          <Col span="4">
                            <FormItem label="系统资源:" prop="issys">
                              <i-switch v-model="mainResourceFormModel.issys" size="large">
                                  <span slot="open">是</span>
                                  <span slot="close">否</span>
                              </i-switch>
                            </FormItem>
                          </Col>
                          <Col span="5">
                              <FormItem label="优先级:" prop="priority" >
                                <InputNumber v-model="mainResourceFormModel.priority"></InputNumber >
                              </FormItem>
                          </Col>
                        </Row>
                        <Row>
                            <Col span="5">
                              <FormItem label="创建人:" prop="createdBy" :label-width="70">
                                <Input v-model="mainResourceFormModel.createdBy" disabled ></Input>
                              </FormItem>
                            </Col>
                            <Col span="6">
                              <FormItem label="创建时间:" prop="createdDate">
                                <Input v-model="mainResourceFormModel.createdDate" disabled></Input>
                              </FormItem>
                            </Col>
                            <Col span="6">
                              <FormItem label="最后更新人:" prop="lastModifiedBy" :label-width="85">
                                <Input v-model="mainResourceFormModel.lastModifiedBy" disabled></Input>
                              </FormItem>
                            </Col>
                            <Col span="7">
                              <FormItem label="最后更新时间:" prop="lastModifiedDate" :label-width="115">
                                <Input v-model="mainResourceFormModel.lastModifiedDate" disabled></Input>
                              </FormItem>
                            </Col>
                        </Row>

                      <Form-item style="text-align:center;margin-left: -58px">
                        <Button type="primary" @click="submitForm('mainResourceForm')">提交</Button>
                        <Button type="ghost" @click="resetForm('mainResourceForm')" style="margin-left: 38px">重置</Button>
                      </Form-item>
                    </Form>
                  </p>
                </Panel>
              </Collapse>
              <h3>页面资源详细信息:</h3>
              <Table :data="contentResourcesTableData" border :loading="loading" :size="tableSize" :columns="contentResourcesTableColumns" height="435"></Table>

            </Col>

            <Modal v-model="editContentResourcesModal" width="850" :mask-closable="false" :scrollable="true" >
                <p slot="header" style="color:#3399ff;text-align:left">
                    <Icon type="icon-addpeople_fill"></Icon>
                    <span>修改页面资源</span>
                </p>

                <Form ref="editContentResourcesForm" :model="editContentResourcesFormModel" :rules="resourcesFormRule" :label-width="75">
                    <Row  style="text-align: right">
                        <Col span="8">
                          <FormItem label="资源代码:" prop="resourceId">
                              <Input type="text" v-model="editContentResourcesFormModel.resourceId" disabled  ></Input>
                          </FormItem>
                        </Col>

                        <Col span="8">
                          <FormItem label="唯一标识:" prop="globalUniqueId" :label-width="75">
                              <Input v-model="editContentResourcesFormModel.globalUniqueId" placeholder="请输入唯一标识码"></Input>
                          </FormItem>
                        </Col>

                        <Col span="8">
                          <FormItem label="资源名称:" prop="resourceName">
                            <Input v-model="editContentResourcesFormModel.resourceName" placeholder="请输入资源名称"></Input>
                          </FormItem>
                        </Col>
                    </Row>

                    <Row  style="text-align: right">
                        <Col span="8">
                          <FormItem label="父资源代码:" prop="fatherResourceId" :label-width="86">
                              <Input v-model="editContentResourcesFormModel.fatherResourceId" disabled></Input>
                          </FormItem>
                        </Col>
                        <Col span="6">
                          <FormItem label="资源类型:" prop="resourceType">
                              <Select v-model="editContentResourcesFormModel.resourceType" placeholder="请选择" style="text-align: center">
                                <Option v-for="item in resourceTypeList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                              </Select>
                          </FormItem>
                        </Col>
                        <Col span="6">
                          <FormItem label="请求方法:" prop="requestMethod">
                              <Select v-model="editContentResourcesFormModel.requestMethod" placeholder="请选择" style="text-align: center">
                                <Option v-for="item in requestMethodList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                              </Select>
                          </FormItem>
                        </Col>

                        <Col span="4">
                            <FormItem label="是否可用:" prop="enable">
                              <i-switch v-model="editContentResourcesFormModel.enable" size="large">
                                  <span slot="open">可用</span>
                                  <span slot="close">不可</span>
                              </i-switch>
                            </FormItem>
                        </Col>
                    </Row>

                    <Row  style="text-align: right">
                        <Col span="10">
                          <FormItem label="资源路径:" prop="resourcePath">
                              <Input v-model="editContentResourcesFormModel.resourcePath"></Input>
                          </FormItem>
                        </Col>
                        <Col span="4">
                          <FormItem label="系统资源:" prop="issys">
                            <i-switch v-model="editContentResourcesFormModel.issys" size="large">
                                <span slot="open">是</span>
                                <span slot="close">否</span>
                            </i-switch>
                          </FormItem>
                        </Col>
                        <Col span="5">
                            <FormItem label="图标:" prop="icon">
                              <Input v-model="editContentResourcesFormModel.icon"></Input>
                            </FormItem>
                        </Col>
                        <Col span="5">
                            <FormItem label="优先级:" prop="priority" >
                              <InputNumber v-model="editContentResourcesFormModel.priority"></InputNumber >
                            </FormItem>
                        </Col>
                    </Row>
                    <Row  style="text-align: right">
                          <FormItem label="资源描述" prop="resourceDesc">
                              <Input type="textarea" v-model="editContentResourcesFormModel.resourceDesc" :rows="2" placeholder="请输入..."></Input>
                          </FormItem>
                    </Row>

                    <Form-item style="text-align:center;margin-left: -58px">
                      <Button type="primary" @click="submitForm('editContentResourcesForm')">提交</Button>
                      <Button type="ghost" @click="resetForm('editContentResourcesForm')" style="margin-left: 38px">重置</Button>
                    </Form-item>
                </Form>
                <div slot="footer" style="text-align:right">
                </div>
            </Modal>
        </Row>
    </div>
</template>
<script>
    import {hierarchicalEdgeBundling} from 'vued3tree'

    export default {
      name: 'resourcelist',
      components: {
        hierarchicalEdgeBundling
      },
      data () {
        return {
          editContentResourcesModal: false,
          editContentResourcesFormModel: {
            createdBy: '',
            createdDate: '',
            enable: true,
            fatherResourceId: '0',
            globalUniqueId: '',
            icon: '',
            issys: true,
            lastModifiedBy: '',
            lastModifiedDate: '',
            priority: 0,
            resourceDesc: '',
            resourceId: '',
            resourceName: '',
            resourcePath: '',
            resourceType: '',
            requestMethod: ''
          },
          contentResourcesTableData: [],
          loading: false,
          tableSize: 'small',
          contentResourcesTableColumns: [
            {
              title: '资源名称',
              key: 'resourceName',
              align: 'left',
              width: 110,
              fixed: 'left',
              sortable: true
            },
            {
              title: '唯一标识',
              key: 'globalUniqueId',
              width: 250,
              align: 'left'
            },
            {
              title: '资源类型',
              align: 'left',
              width: 100,
              key: 'resourceType'
            },
            {
              title: '请求方法',
              align: 'left',
              width: 100,
              key: 'requestMethod'
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
              title: '系统资源',
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
              title: '资源路径',
              align: 'left',
              width: 220,
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
              width: 120,
              key: 'fatherResourceId'
            },
            {
              title: '图标',
              align: 'left',
              width: 80,
              key: 'icon'
            },
            {
              title: '优先级',
              align: 'left',
              width: 80,
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
              width: 120,
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
                        this.editContentResources(params.row)
                      }
                    }
                  }, '编辑'),
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
                        this.deleteContentResources(params.row.resourceId, params.index)
                      }
                    }
                  }, '删除')
                ])
              }
            }
          ],
          resourceTypeList: [
            {
              value: 'MENU',
              label: '菜单'
            },
            {
              value: 'URL',
              label: '请求路径'
            }
          ],
          requestMethodList: [
            {
              value: 'GET',
              label: 'GET'
            },
            {
              value: 'POST',
              label: 'POST'
            },
            {
              value: 'PUT',
              label: 'PUT'
            },
            {
              value: 'DELETE',
              label: 'DELETE'
            }
          ],
          mainResourceFormModel: {
            createdBy: '',
            createdDate: '',
            enable: true,
            fatherResourceId: '0',
            globalUniqueId: '',
            icon: '',
            issys: true,
            lastModifiedBy: '',
            lastModifiedDate: '',
            priority: 0,
            resourceDesc: '',
            resourceId: '',
            resourceName: '',
            resourcePath: '',
            resourceType: '',
            requestMethod: ''
          },
          resourceTreeData: [],
          resourcesFormRule: {
            globalUniqueId: [
              { required: true, message: '唯一标识不能为空', trigger: 'blur' }
            ],
            resourceName: [
              { required: true, message: '资源名称不能为空', trigger: 'blur' }
            ],
            fatherResourceId: [
              { required: true, message: '父资源代码不能为空', trigger: 'blur' }
            ],
            resourceType: [
              { required: true, message: '资源类型不能为空', trigger: 'blur' }
            ],
            requestMethod: [
              { required: true, message: '请求方法不能为空', trigger: 'blur' }
            ],
            enable: [
              { type: 'boolean', required: true, message: '请选择是否可用', trigger: 'blur' }
            ],
            resourcePath: [
              { required: true, message: '资源路径不能为空', trigger: 'blur' }
            ],
            priority: [
              { type: 'integer', message: '优先级必须为数字', trigger: 'blur' }
            ]
          }
        }
      },
      mounted: function () {
        this.queryMenuTree()
      },
      methods: {
        resourceSelectChange (data) {
          console.log(JSON.stringify(data[0]))
          this.queryMenuMoreinfo(data[0].resourceId)
        },
        queryMenuMoreinfo (resourceId) {
          this.$api.requestUtil('/resource/moreinfo/' + resourceId, 'get', '').then(res => {
            console.log(res)
            if (res.responseCode === '00000') {
              console.log(res)
              this.mainResourceFormModel = res.data.mainResource
              this.contentResourcesTableData = res.data.contentResources
            }
          }).catch(err => {
            this.loading = false
            console.error('哎哟~！', err)
          })
        },
        queryMenuTree () {
          this.$api.requestUtil('/resource/menu', 'get', '').then(res => {
            console.log(res)
            if (res.responseCode === '00000') {
              this.resourceTreeData = res.data
            }
          }).catch(err => {
            this.loading = false
            console.error('哎哟~！', err)
          })
        },
        editContentResources (resource) {
          this.editContentResourcesFormModel = resource
          this.editContentResourcesModal = true
        },
        deleteContentResources (resourceId, index) {
          this.$Modal.confirm({
            title: '删除资源',
            content: '<h3> 确定需要删除吗？</h3>',
            loading: true,
            onOk: () => {
              this.requestDeleteContentResources(resourceId, index)
            }
          })
        },
        requestDeleteContentResources (resourceId, index) {
          this.$api.requestUtil('/resource/' + resourceId, 'delete', '').then(res => {
            if (res.responseCode === '00000') {
              this.$Modal.remove()
              this.contentResourcesTableData.splice(index, 1)
              this.$Message.success('删除成功!')
            }
          }).catch(err => {
            this.loading = false
            console.error('哎哟~！', err)
          })
        },
        resetForm (name) {
          this.$refs[name].resetFields()
        },
        submitForm (name) {
          this.$refs[name].validate((valid) => {
            if (valid) {
              if (name === 'editContentResourcesForm') {
                this.$api.requestUtil('/resource', 'post', this.editContentResourcesFormModel).then(res => {
                  if (res.responseCode === '00000') {
                    this.editContentResourcesFormModel = res.data
                    this.queryMenuMoreinfo(this.editContentResourcesFormModel.fatherResourceId)
                    this.$Message.success('提交成功!')
                  }
                }).catch(err => {
                  this.loading = false
                  console.error('哎哟~！', err)
                })
              } else if (name === 'mainResourceForm') {
                this.$api.requestUtil('/resource', 'post', this.mainResourceFormModel).then(res => {
                  if (res.responseCode === '00000') {
                    this.mainResourceFormModel = res.data
                    this.queryMenuMoreinfo(this.mainResourceFormModel.resourceId)
                    this.$Message.success('提交成功!')
                  }
                }).catch(err => {
                  this.loading = false
                  console.error('哎哟~！', err)
                })
              }
            } else {
              this.$Message.error('表单验证失败!')
            }
          })
        },
        addContentResourcesButton () {
          if (this.mainResourceFormModel.resourceId === '') {
            this.$Message.error('请选择菜单!')
          } else {
            this.editContentResourcesFormModel = {}
            this.editContentResourcesFormModel.fatherResourceId = this.mainResourceFormModel.resourceId
            this.editContentResourcesModal = true
          }
        },
        addMainResourceButton (menutype) {
          if (menutype === 'root') {
            this.mainResourceFormModel = {}
            this.mainResourceFormModel.fatherResourceId = '0'
          } else {
            if (this.mainResourceFormModel.resourceId === '') {
              this.$Message.error('请选择菜单!')
            } else {
              let fatherResourceId = this.mainResourceFormModel.resourceId
              this.mainResourceFormModel = {}
              this.mainResourceFormModel.fatherResourceId = fatherResourceId
            }
          }
        },
        delMainResourceButton () {
          this.$Modal.confirm({
            title: '删除资源',
            content: '<h3> 确定需要删除 ' + this.mainResourceFormModel.resourceName + ' 吗？</h3>',
            loading: true,
            onOk: () => {
              this.requestDeleteMainResource(this.mainResourceFormModel.resourceId)
            }
          })
        },
        requestDeleteMainResource (resourceId) {
          this.$api.requestUtil('/resource/' + resourceId, 'delete', '').then(res => {
            if (res.responseCode === '00000') {
              this.$Modal.remove()
              this.queryMenuTree()
              this.mainResourceFormModel = {}
              this.contentResourcesTableData = {}
              this.$Message.success('删除成功!')
            }
          }).catch(err => {
            this.loading = false
            console.error('哎哟~！', err)
          })
        }
      }
    }
</script>
