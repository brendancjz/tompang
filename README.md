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

4. Create and update your `alternatedocroot_1` `value` in `glassfish-web.xml`. `FILES > Tompang-war > web > WEB-INF > glassfish-web.xml`

5. Create and update your `alternatedocroot_1` `value` in `web.xml` as well. `FILES > Tompang-war > web > WEB-INF > web.xml`

6. Start glassfish server. Open Tompang project, including required projects. Add the corresponding Libraries. `Clean and Build > Deploy`  

7. In `tompang-app`, install relevant plugins with `npm install`. 
8. Create `environment.prod.ts` in `environments` with `IP_ADDRESS`. 
9. To run Tompang mobile app on `localhost`, `ionic serve`.
