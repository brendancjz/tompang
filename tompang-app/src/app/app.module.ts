import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { HeaderPageModule } from './header/header.module';
import { FooterPageModule } from './footer/footer.module';
import { enterAnimation } from './animations/nav-animation';
import { BarcodeScanner } from '@ionic-native/barcode-scanner/ngx';
import { Camera } from '@awesome-cordova-plugins/camera/ngx';

@NgModule({
  declarations: [AppComponent],
  entryComponents: [],
  imports: [
    BrowserModule,
    IonicModule.forRoot({
      navAnimation: enterAnimation,
    }),
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    HeaderPageModule,
    FooterPageModule,
  ],
  providers: [
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy },
    BarcodeScanner,
    Camera
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
