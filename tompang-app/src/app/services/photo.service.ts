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



  async takePicture() {
    console.log('Taking picture in photo service method');
    //Prof code
		// const options: CameraOptions = {
		// 	quality: 100,
		// 	destinationType: this.camera.DestinationType.DATA_URL,
		// 	encodingType: this.camera.EncodingType.JPEG,
		// 	mediaType: this.camera.MediaType.PICTURE
		// };

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

    // const tempImage = await this.camera.getPicture(options);
    // // console.log(tempImage);

    // // Extract just the filename. Result example: cdv_photo_003.jpg
    // const tempFilename = tempImage.substr(tempImage.lastIndexOf('/') + 1);

    // const tempBaseFilesystemPath = tempImage.substr(0, tempImage.lastIndexOf('/') + 1);

    // const newBaseFilesystemPath = this.file.dataDirectory;
    // console.log('here');
    // await this.file.copyFile(tempBaseFilesystemPath, tempFilename,
    //   newBaseFilesystemPath, tempFilename);
    // console.log('here2');
    // const storedPhoto = newBaseFilesystemPath + tempFilename;

    // const displayImage = this.webview.convertFileSrc(storedPhoto);

    // console.log('help lah');
    // console.log(displayImage);
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
      console.log(data+' Uploaded Successfully');
      this.imageFileName = 'not yet';

    }, (err) => {
      console.log('Error', err);

    });

    // const blob = this.dataURItoBlob(this.imageURI);

    // const formData: FormData = new FormData();
    // formData.append('file', blob);

    // this.httpClient.post<any>(this.baseUrl, formData, requestOptions).pipe
    //     (
    //       catchError(this.handleError)
    //     );

    // this.httpClient.post<any>(this.baseUrl, this.imageURI, blobOptions).pipe
    // (
    //   catchError(this.handleError)
    // );

  }

  // dataURItoBlob(dataURI) {
  //   // convert base64/URLEncoded data component to raw binary data held in a string
  //   let byteString;
  //   if (dataURI.split(',')[0].indexOf('base64') >= 0)
  //       {byteString = atob(dataURI.split(',')[1]);}
  //   else
  //       {byteString = unescape(dataURI.split(',')[1]);}

  //   // separate out the mime component
  //   const mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];

  //   // write the bytes of the string to a typed array
  //   const ia = new Uint8Array(byteString.length);
  //   for (let i = 0; i < byteString.length; i++) {
  //       ia[i] = byteString.charCodeAt(i);
  //   }

  //   return new Blob([ia], {type:mimeString});
  // }

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
