<template>
    <div>
        <codemirror 
            ref="codeEditor"
            v-model="DDL"
            :options="codemirrorOptions"
            @input="onCodemirrorSqlChange" 
        ></codemirror>
    </div>
</template>
 
<script>
import { codemirror } from 'vue-codemirror';
import 'codemirror/mode/sql/sql.js'
import 'codemirror/lib/codemirror.css';
import 'codemirror/theme/solarized.css'
 
export default{
    components: {
      codemirror
    },
    data(){
        return {
            DDL: "",
            codemirrorOptions: {
                tabSize: 4,
                styleActiveLine: true,
                lineNumbers: true, // 显示行号
                line: true,
                mode: 'text/x-mssql', // 编辑器模式
                smartIndent: true,
                indentUnit: 4, // 缩进
                autoRefresh: true,
                theme: 'solarized light' //对应引入的主题
            },
        }
    },
    methods:{
        onCodemirrorSqlChange(newSql) {
            this.DDL = newSql
        },
    }
}
</script>
<style scoped>

</style>