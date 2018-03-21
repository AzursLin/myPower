<template>
  <section>
  <el-row>
    <el-col :span="24" class="toolbar">
      <el-form :inline="true" :model="setForm" class="demo-form-inline">
<!--        <el-form-item>
          <el-input v-model="nickName" placeholder="请输入用户名" v-bind:style="{ width: 150 + 'px' }"></el-input>
        </el-form-item>
        <el-form-item>
          或</el-form-item>
        <el-form-item>
          <el-input v-model="name" placeholder="请输入真实姓名、手机号或会员号" v-bind:style="{ width: 250 + 'px' }"></el-input>
        </el-form-item>-->
        <el-form-item>
          <el-button type="primary" @click="addBt">新增</el-button>
        </el-form-item>
          <el-form-item>
          <el-select v-model="searchSelectValue" placeholder="请选择" v-bind:style="{ width: 100 + 'px' }">
            <el-option
              v-for="item in searchOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          </el-form-item>
          <el-form-item>
            <el-input v-model="name" placeholder="请输入对应查找的内容" v-bind:style="{ width: 350 + 'px' }"></el-input>
          </el-form-item>
        <el-form-item>
        <el-button type="primary" @click="searhBt">查找</el-button>
        </el-form-item>
<!--        <el-form-item>
          <el-date-picker
            v-model="startDate"
            type="date"
            placeholder="选择开始日期"
            v-bind:style="{ width: 130 + 'px' }">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
        至</el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="endDate"
            type="date"
            placeholder="选择结束日期"
            v-bind:style="{ width: 130 + 'px' }">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
        <el-button type="primary" @click="searhBt">查询</el-button>
        </el-form-item>-->
      </el-form>
    </el-col>
  </el-row>
    <el-row>
      <el-col :span="24">
        <el-table
          ref="singleTable"
          :data="tableData"
          style="width: 100%"    >
          <el-table-column type="index" width="40">
          </el-table-column>
          <el-table-column property="userName" label="账户登录名">
          </el-table-column>
          <el-table-column property="userNickname" label="账户名">
          </el-table-column>
          <el-table-column property="admin" label="账户权限" :formatter="adminFormatter">
          </el-table-column>
<!--          <el-table-column property="clubRole" label="身份" width="80"  :formatter="roleFormatter">
          </el-table-column>
          <el-table-column property="usageCount" label="使用量（次）" align="right" >
          </el-table-column>
          <el-table-column property="totalKm" label="运动总里程（KM）" align="right">
          </el-table-column>
          <el-table-column property="totalTime" label="运动总时间" align="right" :formatter="totalTimeFormatter">
          </el-table-column>
          <el-table-column property="totalCal" label="消耗卡路里（大卡）" align="right">
          </el-table-column>-->
          <el-table-column  label="设置">
            <template scope="scope">
            <span >
            <el-button
              @click.native.prevent="OpenChangeDialog(scope.$index, 1)"
              type="text"
              size="small">修改</el-button>
          <el-button
            @click.native.prevent="deleteBt(scope.$index)"
            type="text"
            size="small">删除</el-button>
            </span>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="24" class="toolbar">
        <el-pagination layout="prev, pager, next" @current-change="pageChange" :current-page.sync="pageNum"
                       :page-size=pageSize :total="total" style="float:right;">
        </el-pagination>
      </el-col>
    </el-row>

    <el-dialog
      title="详细信息"
      :visible.sync="changeDialog"
      size="tiny">
      <el-form ref="setForm" :model="setForm" label-width="80px">
        <el-form-item label="账户名称">
          <el-input v-model="setForm.userName" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="账户昵称">
          <el-input v-model="setForm.userNickname" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item  label="账户密码">
          <el-input v-model="setForm.password" placeholder="请输入内容"></el-input><span v-if="mode === 1">*为空即不修改</span>
        </el-form-item>
        <el-form-item label="身份">
          <el-select v-model="setForm.admin" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <table style="width: 100%">
        <tr>
          <td align="center">
            <el-button size="large" @click="setBt">
              <span>确认</span>
            </el-button>
          </td>
        </tr>
      </table>
    </el-dialog>

  </section>
</template>

<script>
  import { getUserList,userAdd,userUpdata,userDelete } from '../api/api';
export default {
  name: 'hello',
  data () {
    return {
      options: [{
        value: 1,
        label: '普通用户'
      }, {
        value: 2,
        label: '管理员'
      }],
      searchOptions: [{
        value: 1,
        label: '账户名'
      }, {
        value: 2,
        label: '登录名'
      }],
      searchSelectValue:2,
      tableData:[],
      setForm:{},
      uploadData:{"employeeId":JSON.parse(sessionStorage.getItem('employeeId'))},
      nickName:'',
      name:'',
      startDate:'',
      endDate:'',
      pageNum:1,
      pageSize:8,
      changeDialog:false,
      total:0
    }
    },
    methods: {
      addBt(){
        this.setForm = {
            admin:1
        };
        this.mode = 0;
        this.changeDialog = true;
      },
      roleFormatter(row, column) {
        var role;
        if(row.clubRole === "0") {
          role = '管理员'
        }else if(row.clubRole === "1"){
          role = '教练'
        }else if(row.clubRole === "10"){
          role = '会员'
        }else if(row.clubRole === "11"){
          role = '成员'
        }else {
          role = ''
        }
        return role;
      },
      setBt(){
        let para = {
          'userId':this.setForm.userId,
          'admin':this.setForm.admin,
          'userName':this.setForm.userName,
          'userNickname':this.setForm.userNickname,
          'password':this.setForm.password
        };
        if(this.mode === 1) {
          userUpdata(para).then((res) => {
            let{ret,msg}=res.data;
            if(ret === '1') {
              this.$message({
                message: msg,
                type: 'info'
              });
              this.changeDialog = false;
              this.pageChange(1);
            } else {
              this.$message({
                message: msg,
                type: 'error'
              });
            }
          });
        } else if (this.mode === 0) {
          para.type = this.setForm.type;
          userAdd(para).then((res) => {
            let{ret,msg}=res.data;
            if(ret === '1') {
              this.$message({
                message: msg,
                type: 'info'
              });
              this.changeDialog = false;
              this.pageChange(this.pageNum);
            } else {
              this.$message({
                message: msg,
                type: 'error'
              });
            }
          });
        }

      },
      searhBt(){
        let para = {
          pageNum: 1,
          pageSize: this.pageSize
        };
          if(this.searchSelectValue === 1) {
              para.nickname = this.name;
          } else if(this.searchSelectValue === 2) {
            para.name = this.name;
          }
        this.getPage(para);
      },
      pageChange(val) {
        this.pageNum = val;
        let para = {
          pageNum:this.pageNum,
          pageSize:8
        };
        if(this.searchSelectValue === 1) {
          para.nickname = this.name;
        } else if(this.searchSelectValue === 2) {
          para.name = this.name;
        }
        this.getPage(para);
      },
      getPage(para) {
        getUserList(para).then((res) => {
          if(res.data.ret === '1') {
            this.tableData = res.data.Data;
            this.total = res.data.total;
          } else if(res.data.ret === '0'){
            this.$message({
              message: res.data.msg,
              type: 'error'
            });
          }
        });
      },
      formatDate(date){
        var formatValue;
        if (date === '') {
          formatValue = "";
        } else {
          formatValue = (date.getYear()+1900)
            + "-" + dataLeftCompleting(date.getMonth() + 1)
            + "-" + dataLeftCompleting(date.getDate())
        }
        return formatValue;
      },
      OpenChangeDialog(index,mode) {
        this.mode = mode;
        this.setForm = this.tableData[index];
        this.userValue = this.tableData[index].userId;
        this.changeDialog = true;
      },
      deleteBt(index){
        this.$confirm('确认删除？')
          .then(_ => {
            let para = {
              userId:this.tableData[index].userId
            };
            userDelete(para).then((res) => {
              let{ret,msg}=res.data;
              if(ret === '1') {
                this.$message({
                  message: msg,
                  type: 'info'
                });
                this.changeDialog = false;
                this.pageChange(this.pageNum);
              } else {
                this.$message({
                  message: msg,
                  type: 'error'
                });
              }
            });
          })
          .catch(_ => {});
      },
      adminFormatter(row, column) {
        var formatValue = '';
        if (row.admin === 1) {
          formatValue = '普通用户';
        } else if (row.admin === 2){
          formatValue = '管理员';
        }
        return formatValue;
      },
  },
  mounted() {
    let para = {
      pageNum:1,
      pageSize:8,
      userId:JSON.parse(sessionStorage.getItem('userId'))
    };
    this.getPage(para);
  }
}

  function dataLeftCompleting(value){
    return parseInt(value) < 10 ? "0" + value : value;
  }
</script>

<style>

  .el-upload-list--picture-card .el-upload-list__item  {
    width: 90px;
    height: 90px;
  }
  .el-upload el-upload--picture-card {
    font-size: 28px;
    color: #8c939d;
    width: 90px;
    height: 90px;
    line-height: 90px;
    text-align: center;
  }
  .el-upload--picture-card {
    width: 90px;
    height: 90px;
    line-height: 90px;
  }
</style>

