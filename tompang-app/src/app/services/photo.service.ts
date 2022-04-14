import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';

import { Camera, CameraOptions } from '@awesome-cordova-plugins/camera/ngx';
import { File as NgxFile } from '@ionic-native/file/ngx';
import { FileTransfer, FileUploadOptions, FileTransferObject } from '@ionic-native/file-transfer/ngx';

import { User } from '../models/user';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { SessionService } from './session.service';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PhotoService
{
  baseUrl = 'http://' + this.sessionService.ipAddress + ':8080/TompangRws/Resources/File/';
  currentUser: User;

  imageURI: any;
  imageFileName: any;

  constructor(private camera: Camera,
    private httpClient: HttpClient,
    private file: NgxFile,
    private transfer: FileTransfer,
    private sessionService: SessionService)
  {
    console.log('photo service constructor');
  }



  takePicture(): void {
    console.log('Taking picture in photo service method');

    const options: CameraOptions = {
			quality: 50,
			destinationType: this.camera.DestinationType.FILE_URI,
			encodingType: this.camera.EncodingType.JPEG,
			mediaType: this.camera.MediaType.PICTURE,
		};

    this.camera.getPicture(options).then((imageData) => {
      console.log('This is the step after getting the Picture');
      this.imageURI = imageData;
      console.log('ImageURI', this.imageURI);

      this.uploadFile();
    }, (err) => {
      console.log(err);

    });
  }

  uploadFile() {
    const fileTransfer: FileTransferObject = this.transfer.create();

    // eslint-disable-next-line @typescript-eslint/ban-types
    const requestOptions: Object = {responseType : 'text'};
    // eslint-disable-next-line @typescript-eslint/ban-types
    const blobOptions: Object = {responseType : 'blob'};

    const options: FileUploadOptions = {
      fileKey: 'file',
      fileName: this.imageURI.split('/cache/')[1],
      httpMethod: 'POST',
      chunkedMode: false,
      mimeType: 'image/jpeg',
      headers: {fileName:this.imageURI.split('/cache/')[1]}
    };

    //500 error
    console.log(options);
    fileTransfer.upload(this.imageURI, this.baseUrl, options)
    .then((data) => {
      console.log('Uploaded Successfully', data);
      this.imageFileName = this.imageURI.split('/cache/')[1];
      return this.imageFileName;
    }, (err) => {
      console.log('Error', err);
    });

  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = '';

    if (error.error instanceof ErrorEvent) {
      errorMessage = 'An unknown error has occurred: ' + error.error.message;
    }
    else {

      //I added this because 200 shouldnt be here but it is somehow. Help
      if (`${error.status}` === '200') {
        console.log('200 OK File Upload');
        return new Observable();
      }

      errorMessage =
        'A HTTP error has occurred: ' +
        `HTTP ${error.status}: ${error.error.message}`;
    }

    return throwError(errorMessage);
  }

}
