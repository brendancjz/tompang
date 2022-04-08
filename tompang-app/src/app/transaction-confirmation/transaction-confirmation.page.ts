import { Component, OnInit } from '@angular/core';
import {
  BarcodeScanner,
  BarcodeScannerOptions,
} from '@ionic-native/barcode-scanner/ngx';

@Component({
  selector: 'app-transaction-confirmation',
  templateUrl: './transaction-confirmation.page.html',
  styleUrls: ['./transaction-confirmation.page.scss'],
})
export class TransactionConfirmationPage implements OnInit {
  encodedData: any;
  scannedQRCode: {};
  QRScannerOptions: BarcodeScannerOptions;

  qrData = 'https://www.example.com';
  scannedCode = null;
  elementType: 'url' | 'canvas' | 'img' = 'canvas';
  constructor(private scanner: BarcodeScanner) {
    this.QRScannerOptions = {
      showTorchButton: true,
      showFlipCameraButton: true,
    };
  }

  ngOnInit() {}

  scanQRCode() {
    this.scanner
      .scan()
      .then((res) => {
        this.scannedQRCode = res;
      })
      .catch((err) => {
        console.log(err);
      });
  }

  generateQRCode() {
    this.scanner.encode(this.scanner.Encode.TEXT_TYPE, this.encodedData).then(
      (res) => {
        console.log('generated QR: ' + res);
        this.encodedData = res;
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
