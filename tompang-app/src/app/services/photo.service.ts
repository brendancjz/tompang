import { Injectable } from '@angular/core';

import { Camera, CameraOptions } from '@awesome-cordova-plugins/camera/ngx';
import { File, FileEntry } from '@ionic-native/file/ngx';
import { User } from '../models/user';
import { FileUploadService } from './fileUpload.service';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PhotoService
{
  baseUrl = '/api/File';
  currentUser: User;

  constructor(private camera: Camera,
    private fileUploadService: FileUploadService,
    private httpClient: HttpClient)
  {
    console.log('photo service constructor');
  }



  takePicture(): Observable<any> {
    console.log('Taking picture in photo service method');
    //Prof code
		// const options: CameraOptions = {
		// 	quality: 100,
		// 	destinationType: this.camera.DestinationType.DATA_URL,
		// 	encodingType: this.camera.EncodingType.JPEG,
		// 	mediaType: this.camera.MediaType.PICTURE
		// };

    const options: CameraOptions = {
			quality: 100,
			destinationType: this.camera.DestinationType.FILE_URI,
			encodingType: this.camera.EncodingType.JPEG,
			mediaType: this.camera.MediaType.PICTURE
		};

    const file = new File();

		this.camera.getPicture(options).then((imageData) => {
      // Do something with the new photo
      console.log('Camera image captured');
      console.log(imageData);

      file.resolveLocalFilesystemUrl(imageData).then((entry: FileEntry) => {
        // eslint-disable-next-line @typescript-eslint/no-shadow
        entry.file(file => {
          console.log('Inside File Entry');
          console.log(file);

          const reader = new FileReader();
          reader.onload = () => {
            const blob = new Blob([reader.result], {
              type: file.type
            });

            const formData = new FormData();
            formData.append('file', blob, file.name);

            // eslint-disable-next-line @typescript-eslint/ban-types
            const requestOptions: Object = {responseType : 'text'};
            console.log('code reaches here');
            return this.httpClient.post<any>(this.baseUrl + '/upload', formData, requestOptions).pipe(
              catchError(this.handleError)
              );
          };

          reader.readAsArrayBuffer(file);
        });
      }, (err) => {
      // Handle error
      console.log('Camera issue: ' + err);
      ;
    });
    });

    console.log('codes runs here');
    return new Observable<any>();
  }

  private handleError(error: HttpErrorResponse) {
    console.log('goes into error msg');

    let errorMessage = '';

    if (error.error instanceof ErrorEvent) {
      errorMessage = 'An unknown error has occurred: ' + error.error.message;
    }
    else {

      errorMessage = 'A HTTP error has occurred: ' + `HTTP ${error.status}: ${error.error.message}`;
    }


    return throwError(errorMessage);
  }
}
