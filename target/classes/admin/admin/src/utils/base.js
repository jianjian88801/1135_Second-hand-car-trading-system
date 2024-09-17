const base = {
    get() {
        return {
            url : "http://localhost:8080/ershouchejiaoyi/",
            name: "ershouchejiaoyi",
            // 退出到首页链接
            indexUrl: 'http://localhost:8080/ershouchejiaoyi/front/index.html'
        };
    },
    getProjectName(){
        return {
            projectName: "二手车交易系统"
        } 
    }
}
export default base
