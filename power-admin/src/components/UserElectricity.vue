<template>
  <section>
  <el-row>
    <el-col :span="24" class="toolbar">
      <el-form :inline="true" :model="setForm" class="demo-form-inline">
        <el-form-item v-if="userAdmin == 2">
          <el-button type="primary" @click="addBt" >新增</el-button>
        </el-form-item>
        <el-form-item v-if="userAdmin == 2">
          <el-select v-model="searchSelectValue" placeholder="请选择" v-bind:style="{ width: 100 + 'px' }">
            <el-option
              v-for="item in searchOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="userAdmin == 2">
          <el-input v-model="name" placeholder="请输入对应查找的内容" v-bind:style="{ width: 350 + 'px' }"></el-input>
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="dateValue"
            type="daterange"
            placeholder="选择日期范围">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
        <el-button type="primary" @click="searchBt">查找</el-button>
        </el-form-item>
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
          <el-table-column property="userNickname" label="账户名">
          </el-table-column>
          <el-table-column property="userRead" label="用电度数">
          </el-table-column>
          <el-table-column property="money" label="费用">
          </el-table-column>
          <el-table-column property="createDate" label="月份" :formatter="createDateFormatter">
          </el-table-column>
          <el-table-column property="pay" label="是否缴清" :formatter="payFormatter">
          </el-table-column>
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
        <el-form-item label="账户" v-if="userAdmin === 2">
        <el-select v-model="userValue" clearable placeholder="请选择">
          <el-option v-for="item in userList" :label="item.userNickname" :value="item.userId"></el-option>
        </el-select>
        </el-form-item>
        <el-form-item label="用电度数">
          <el-input v-model="setForm.userRead" type="number" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="费用">
          <el-input v-model="setForm.money" type="number" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="月份">
          <el-date-picker
            v-model="setForm.createDate"
            type="date"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否缴清">
          <el-select v-model="setForm.pay" placeholder="请选择">
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
  import { getUserElectricity,userElectricityAdd,getUserList,userElectricityUpdata,userElectricityDelete } from '../api/api';
export default {
  name: 'hello',
  data () {
    return {
      options: [{
        value: 0,
        label: '未缴清'
      }, {
        value: 1,
        label: '已缴清'
      }],
      searchOptions: [{
        value: 0,
        label: '账户名'
      }],
      tableData:[],
      setForm:{},
      userList:[''],
      userValue:'',
      userAdmin:JSON.parse(sessionStorage.getItem('admin')),
      nickName:'',
      name:'',
      startDate:'',
      endDate:'',
      searchSelectValue:0,
      pageNum:1,
      pageSize:8,
      changeDialog:false,
      total:0,
      dateValue:[]
    }
    },
    methods: {
      addBt(){
        this.setForm = {
          pay:0
        };
        this.mode = 0;
        if(this.userAdmin === 2) {
          let para = {
            pageNum:1,
            pageSize:8,
            userId:JSON.parse(sessionStorage.getItem('userId'))
          };
          getUserList(para).then((res) => {
            if(res.data.ret === '1') {
              console.log(res.data.Data) ;
              this.userList = res.data.Data;
            } else if(res.data.ret === '0'){
              this.$message({
                message: res.data.msg,
                type: '获取失败'
              });
            }
          });
        }

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
        var userId = 1;
        if(this.userAdmin === 2) {
          userId = this.userValue;
        } else {
          userId = JSON.parse(sessionStorage.getItem('userId'));
        }
        let para = {
          'id':this.setForm.id,
          'userRead':this.setForm.userRead,
          'money':this.setForm.money,
          'createDate':this.setForm.createDate,
          'pay':this.setForm.pay,
          'userId':userId
        };
        if(this.mode === 1) {
          userElectricityUpdata(para).then((res) => {
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
          userElectricityAdd(para).then((res) => {
            let{ret,msg}=res.data;
            if(ret === '1') {
              this.$message({
                message: msg,
                type: 'info'
              });
              this.changeDialog = false;
              let para = {
                pageNum:1,
                pageSize:8
              };
              this.getPage(para);
            } else {
              this.$message({
                message: msg,
                type: 'error'
              });
            }
          });
        }

      },
      searchBt(){
        this.pageChange(1);
      },
      pageChange(val) {
        this.pageNum = val;
        let para = {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          startDate:this.dateValue[0],
          endDate:this.dateValue[1]
        };
        if(this.searchSelectValue === 0) {
          para.userName = this.name;
        }
        this.getPage(para);
      },
      getPage(para) {
        getUserElectricity(para).then((res) => {
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
        if(this.userAdmin === 2) {
          let para = {
            pageNum:1,
            pageSize:8
          };
          getUserList(para).then((res) => {
            if(res.data.ret === '1') {
              console.log(res.data.Data) ;
              this.userList = res.data.Data;
            } else if(res.data.ret === '0'){
              this.$message({
                message: res.data.msg,
                type: '获取失败'
              });
            }
          });
        }
        this.changeDialog = true;
      },
      deleteBt(index){
        this.$confirm('确认删除？')
          .then(_ => {
            let para = {
              id:this.tableData[index].id
            };
            userElectricityDelete(para).then((res) => {
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
      createDateFormatter(row, column) {
        if (row.dataTime === '') {
          formatValue = "";
        } else {
          var newDate = new Date();
          newDate.setTime(row.dataTime);
          var startDate = newDate;
          var formatValue = startDate.toLocaleDateString()
          /*          var formatValue = (startDate.getYear())
            + "-" + dataLeftCompleting(startDate.getMonth() + 1)
            + "-" + dataLeftCompleting(startDate.getDate())
            + " " + dataLeftCompleting(startDate.hours)
            + ":" + dataLeftCompleting(startDate.minutes)
            + ":" + dataLeftCompleting(startDate.seconds);*/
        }
        return formatValue;
      },
      payFormatter(row, column) {
        var formatValue = '';
        if (row.pay === 0) {
          formatValue = '未付清';
        } else if (row.pay === 1){
          formatValue = '已付清';
        }
        return formatValue;
      },
  },
  mounted() {
    let para = {
      pageNum:1,
      pageSize:8
    };
    this.getPage(para);
  }
}

  function dataLeftCompleting(value){
    return parseInt(value) < 10 ? "0" + value : value;
  }

  function dataLeftCompleting(value){
    return parseInt(value) < 10 ? "0" + value : value;
  }

  function isEmptyObject(obj) {
    for (var i in obj) {
      return false
    }
    return true
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

