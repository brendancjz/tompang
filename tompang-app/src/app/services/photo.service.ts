import { Injectable } from '@angular/core';

import { Camera, CameraOptions } from '@awesome-cordova-plugins/camera/ngx';
import { File as NgxFile, FileEntry } from '@ionic-native/file/ngx';
import { User } from '../models/user';
import { FileUploadService } from './fileUpload.service';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { WebView } from '@awesome-cordova-plugins/ionic-webview/ngx';

@Injectable({
  providedIn: 'root'
})
export class PhotoService
{
  baseUrl = '/api/File';
  currentUser: User;

  constructor(private camera: Camera,
    private fileUploadService: FileUploadService,
    private httpClient: HttpClient,
    private file: NgxFile,
    private webview: WebView)
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
			mediaType: this.camera.MediaType.PICTURE
		};

    const tempImage = await this.camera.getPicture(options);
    // console.log(tempImage);

    // Extract just the filename. Result example: cdv_photo_003.jpg
    const tempFilename = tempImage.substr(tempImage.lastIndexOf('/') + 1);

    const tempBaseFilesystemPath = tempImage.substr(0, tempImage.lastIndexOf('/') + 1);

    const newBaseFilesystemPath = this.file.dataDirectory;
    console.log('here');
    await this.file.copyFile(tempBaseFilesystemPath, tempFilename,
      newBaseFilesystemPath, tempFilename);
    console.log('here2');
    const storedPhoto = newBaseFilesystemPath + tempFilename;

    const displayImage = this.webview.convertFileSrc(storedPhoto);

    console.log('help lah');
    console.log(displayImage);
  }

}
