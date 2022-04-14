# Tompang
<div style="display: flex; align-items: center; justify-content: space-around; width: 40%; margin: 0 auto;">
  <img src="https://github.com/brendancjz/tompang/blob/main/Tompang/Tompang-war/web/resources/images/tompang_icon_logo_white.png" width="100">                        
  <img src="https://github.com/brendancjz/tompang/blob/main/Tompang/Tompang-war/web/resources/images/tompang_logo_white.png" width="200">  
</div>

## Getting Started Running Locally

### Clone this repository

Use `gh repo clone brendancjz/tompang` to get the files within this repository onto your local machine

### Environment Setup

1. Create a new database `tompang`.  

2. Change user.properties name in `FILES > Tompang-ejb > nbproject > private > private.xml`

3. Change username or password in glassfish-resources.xml to your own

4. Update your `alternatedocroot_1` `value` in `glassfish-web.xml`. `FILES > Tompang-war > web > WEB-INF > glassfish-web.xml`

For Windows,
`<property name="alternatedocroot_1" value="from=/uploadedFiles/* dir=C:/glassfish-5.1.0-uploadedfiles"/>`
For Mac,
`<mac command>`
5. Update your `alternatedocroot_1` `value` in `web.xml` as well. `FILES > Tompang-war > web > WEB-INF > web.xml`

For Windows,
`<context-param>
    <param-name>alternatedocroot_1</param-name>
    <param-value>C:/glassfish-5.1.0-uploadedfiles/uploadedFiles</param-value>
 </context-param>`
 For Mac,
 `<mac command>`
6. Start glassfish server. Open Tompang project, including required projects. Add the corresponding Libraries. Clean and Build > Deploy  
