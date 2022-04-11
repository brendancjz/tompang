import { Injectable } from '@angular/core';

import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

const httpOptions = {
  // eslint-disable-next-line @typescript-eslint/naming-convention
  headers: new HttpHeaders({ 'Content-Type': 'multipart/form-data' })
};

@Injectable({
  providedIn: 'root',
})
export class FileUploadService {
  baseUrl = '/api/File';

  constructor(private httpClient: HttpClient) {
  }

  uploadFile(fileToUpload: File | null): Observable<any> {
    console.log('Uploading file...');
    console.log(fileToUpload);
    // eslint-disable-next-line @typescript-eslint/ban-types
    const requestOptions: Object = {responseType : 'text'};

    if (fileToUpload != null) {
      const formData: FormData = new FormData();
      formData.append('file', fileToUpload, fileToUpload.name);

      return this.httpClient.post<any>(this.baseUrl, formData, requestOptions).pipe
        (
          catchError(this.handleError)
        );

      // return this.httpClient.post<any>(this.baseUrl + '/upload', formData, httpOptions).pipe
      // (
      //   catchError(this.handleError)
      // );
    }
    else {
      return new Observable();
    }
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
