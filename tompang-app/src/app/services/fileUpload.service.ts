import { Injectable } from '@angular/core';

import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { SessionService } from './session.service';

const httpOptions = {
  // eslint-disable-next-line @typescript-eslint/naming-convention
  headers: new HttpHeaders({ 'Content-Type': 'multipart/form-data' })
};

@Injectable({
  providedIn: 'root',
})
export class FileUploadService {
  baseUrl = 'http://' + this.sessionService.ipAddress + ':8080/TompangRws/Resources/File/';

  constructor(private httpClient: HttpClient,
    private sessionService: SessionService) {
  }

  uploadFile(fileToUpload: File | null): Observable<any> {
    console.log('Uploading file...');
    console.log(fileToUpload);
    // eslint-disable-next-line @typescript-eslint/ban-types
    const requestOptions: Object = {responseType : 'text'};

    if (fileToUpload != null) {
      const formData: FormData = new FormData();
      formData.append('file', fileToUpload, fileToUpload.name);

      return this.httpClient.post<any>(this.baseUrl + 'upload/', formData, requestOptions).pipe
        (
          catchError(this.handleError)
        );
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
