<template>
    <div>
      <el-row>
         <el-col :span="3">
            <div class="operate-button-group">
               <el-button plain class="operate-button" @click="getFile">test</el-button>
               <el-button plain class="operate-button">新增字段</el-button>
               <el-button type="primary" class="operate-button">生成代码</el-button>
               <el-button type="danger" class="operate-button">清空字段</el-button>
            </div>
         </el-col>
         <el-col :span="4">
            <div>
               <div>
                  <el-row>
                     执行器
                     <el-radio-group v-model="XCParameters.executorType">
                        <el-radio-button label="A"></el-radio-button>
                        <el-radio-button label="B"></el-radio-button>
                        <el-radio-button label="C"></el-radio-button>
                        <el-radio-button label="D"></el-radio-button>
                     </el-radio-group>
                  </el-row>
                  <el-row>
                     执行器组件
                     <el-radio-group v-model="XCParameters.executorType">
                        <el-radio-button label="A"></el-radio-button>
                        <el-radio-button label="B"></el-radio-button>
                        <el-radio-button label="C"></el-radio-button>
                        <el-radio-button label="D"></el-radio-button>
                     </el-radio-group>
                  </el-row>
                  <el-row>
                     基本信息
                     <el-form ref="baseInfo" :model="XCParameters" label-width="100px">
                        <el-form-item label="执行器">
                           <el-input v-model="XCParameters.executorType" placeholder="请选择执行器"></el-input>
                        </el-form-item>
                        <el-form-item label="输出路径">
                           <el-input v-model="XCParameters.parentPath" placeholder="请输入文件输出路径"></el-input>
                        </el-form-item>
                     </el-form>
                  </el-row>
               </div>
               <div>
                  字段列表
               </div>
            </div>
         </el-col>
         <el-col :span="6">
            <div>
               字段信息
               <el-form ref="property" :model="property" label-width="100px">
                  <el-form-item label="字段名">
                     <el-input v-model="property.propertyName" placeholder="请输入字段名"></el-input>
                  </el-form-item>
                  <el-form-item label="属性名">
                     <el-input v-model="property.fieldName" placeholder="请输入属性名"></el-input>
                  </el-form-item>
                  <el-form-item label="字段类型">
                     <el-select v-model="property.propertyType" placeholder="请选择字段类型">
                        <el-option
                           v-for="item in propertyTypeOptions"
                           :key="item.value"
                           :label="item.label"
                           :value="item.value">
                        </el-option>
                     </el-select>
                  </el-form-item>
                  <el-form-item label="属性类型">
                     <el-select v-model="property.fieldType" placeholder="请选择属性类型">
                        <el-option
                           v-for="item in fieldTypeOptions"
                           :key="item.value"
                           :label="item.label"
                           :value="item.value">
                        </el-option>
                     </el-select>
                  </el-form-item>
                  <el-form-item label="描述">
                     <el-input v-model="property.description" placeholder="请输入描述"></el-input>
                  </el-form-item>
                  <el-form-item label="">
                     <template slot="label">
                        <span>是否显示
                           <el-tooltip class="item"
                                       effect="dark"
                                       content="列表或表单是否显示"
                                       placement="left">
                           <i class="el-icon-question"
                              style="font-size: 16px; vertical-align: middle;"></i>
                           </el-tooltip>
                        </span>
                     </template>
                     <el-switch
                        v-model="property.isShow"
                        active-color="#13ce66"
                        inactive-color="#ff4949">
                     </el-switch>
                  </el-form-item>
                  <el-form-item label="是否必填">
                     <el-switch
                        v-model="property.isRequired"
                        active-color="#13ce66"
                        inactive-color="#ff4949">
                     </el-switch>
                  </el-form-item>
               </el-form>
            </div>
         </el-col>
         <el-col :span="11">
            <div>
               <el-row>
                  执行器组件
                  <el-radio-group v-model="XCParameters.executorType">
                     <el-radio-button label="A"></el-radio-button>
                     <el-radio-button label="B"></el-radio-button>
                     <el-radio-button label="C"></el-radio-button>
                     <el-radio-button label="D"></el-radio-button>
                  </el-radio-group>
               </el-row>
               <el-row>
                  预览代码
                  <highlightjs :code="code"></highlightjs>
               </el-row>
            </div>
         </el-col>
      </el-row>
    </div>
</template>
<script>

import { getFile } from '@/api/generator.js'

export default {
   name:'Generator',
   components:{
      
   },
   data() {
      return {
         code: "",
         generatorVo: {
            allExecutorList: [],
            executorList: [],
            allFreeMakerList: [],
            freeMakerList: [],
         },
         XCParameters:{
            table:{
               packagePath: "",
               className: "",
               tableName: "",
               title: "",
               properties: [],
               required: [],
               version: "",
            },
            executorType: "",
            parentPath: "",
         },
         property: {},
         propertyTypeOptions:[
            {
               value: 'VARCHAR',
               label: 'VARCHAR'
            },
            {
               value: 'CHAR',
               label: 'CHAR'
            },
            {
               value: 'DATETIME',
               label: 'DATETIME'
            },
            {
               value: 'DATE',
               label: 'DATE'
            },
         ],
         fieldTypeOptions:[
            {
               value: 'String',
               label: 'String'
            }
         ]
      }
   },
   methods:{
      getFile(){
         getFile().then(response => {
            this.code = response.data;
         });
      },
   },
}
</script>
<style scoped>

.operate-button-group {
   display: flex;
   flex-direction: column;
   border: 2px dashed #cabbbb;
   width: 200px;
   height: 600px;
}

.operate-button {
   margin: 10px 20px;
}

</style>
