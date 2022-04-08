import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { SessionService } from '../../services/session.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';
import { FileUploadService } from 'src/app/services/fileUpload.service';

@Component({
  selector: 'app-change-profile-pic',
  templateUrl: './change-profile-pic.page.html',
  styleUrls: ['./change-profile-pic.page.scss'],
})
export class ChangeProfilePicPage implements OnInit {

  basePicUrl = 'picApi';
  currentProfilePic: string;
  newProfilePic: File | null;
  fileName: string | null;

  editError: boolean;

  currentUser: User;

  constructor(private location: Location,
    public sessionService: SessionService,
    private userService: UserService,
    private fileUploadService: FileUploadService) { }

  ngOnInit() {
    document.getElementById('back-button').addEventListener('click', () => {
      this.resetPage();
    }, { once: true});

    this.currentUser = this.sessionService.getCurrentUser();
  }

  //This code split into two below
  // handleFileInput(event: any) {

  //   this.newProfilePic = event.target.files.item(0);
  //   console.log(this.newProfilePic);

  //   if(this.newProfilePic != null)
  //   {
  //     this.fileName = this.newProfilePic.name;

  //     this.fileUploadService.uploadFile(this.newProfilePic).subscribe(
  //       response => {
  //         this.currentProfilePic = '/uploadedFile/' + this.fileName;
  //         console.log('New Profile pic url: ' + this.currentProfilePic);
  //         console.log('********** FileUploadComponent.ts: File uploaded successfully: ' + response.status);
  //       },
  //       error => {
  //         console.log('********** FileUploadComponent.ts: ' + error);
  //       }
  //     );
  //   }
  // }

  changeProfilePic(event: any) {
    this.newProfilePic = event.target.files.item(0);
    console.log(this.newProfilePic);
  }

  updateProfilePic() {
    if(this.newProfilePic != null)
    {
      this.fileName = this.newProfilePic.name;

      this.fileUploadService.uploadFile(this.newProfilePic).subscribe(
        response => {
          this.currentProfilePic = '/uploadedFile/' + this.fileName;
          console.log('New Profile pic url: ' + this.currentProfilePic);
          console.log('********** FileUploadComponent.ts: File uploaded successfully: ' + response.status);
        },
        error => {
          console.log('********** FileUploadComponent.ts: ' + error);
        }
      );
    }
  }

  displayProfilePic() {
    return 'http://localhost:8080/Tompang-war' + this.currentUser.profilePic;
  }

  updateUserProfilePic(): void {
    console.log('Updating user profile pic...');

  }

  resetPage(): void {
    this.newProfilePic = undefined;
    this.editError = false;
  }
}
