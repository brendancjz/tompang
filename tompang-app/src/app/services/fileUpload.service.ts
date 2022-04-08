import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

const httpOptions = {
	// eslint-disable-next-line @typescript-eslint/naming-convention
	headers: new HttpHeaders({ 'Content-Type': 'multipart/form-data' })
};

@Injectable({
  providedIn: 'root'
})
export class FileUploadService
{
  baseUrl = '/api/File';

  constructor(private httpClient: HttpClient)
  {
  }

  uploadFile(fileToUpload: File | null): Observable<any>
  {
    if(fileToUpload != null)
    {
      const formData: FormData = new FormData();
      formData.append('file', fileToUpload, fileToUpload.name);

      return this.httpClient.post<any>(this.baseUrl + '/upload', formData).pipe
      (
        catchError(this.handleError)
      );
    }
    else
    {
      return new Observable();
    }
  }



  private handleError(error: HttpErrorResponse)
  {
    let errorMessage = '';

    if (error.error instanceof ErrorEvent)
    {
      errorMessage = 'An unknown error has occurred: ' + error.error.message;
    }
    else
    {
      errorMessage = 'A HTTP error has occurred: ' + `HTTP ${error.status}: ${error.error.message}`;
    }

    console.error(errorMessage);

    return throwError(errorMessage);
  }
}
