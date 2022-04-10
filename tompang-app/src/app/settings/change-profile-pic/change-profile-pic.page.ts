import { Component, OnInit } from '@angular/core';
// import { ImagePicker } from '@ionic-native/image-picker';

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

  constructor(public sessionService: SessionService,
    private userService: UserService,
    private fileUploadService: FileUploadService) { }

  ngOnInit() {
    document.getElementById('back-button').addEventListener('click', () => {
      this.resetPage();
    }, { once: true });

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

  // getPicturesFromGallery() {
  //   const options = {
  //     width: 200,
  //     quality: 30,
  //     outputType: 1
  //   };

  //   const imgRes = [];
  //   ImagePicker.getPictures(options).then((results) => {
  //     // eslint-disable-next-line @typescript-eslint/prefer-for-of
  //     for (let i = 0; i < results.length; i++) {
  //       imgRes.push('data:image/jpeg;base64,' + results[i]);
  //       console.log(imgRes);
  //     }
  //   }, (error) => {
  //     alert(error);
  //   });
  // }


  changeProfilePic(event: any) {
    this.newProfilePic = event.target.files.item(0);
    console.log(this.newProfilePic);
  }

  updateProfilePic() {
    console.log(this.newProfilePic);
    console.log(this.newProfilePic.name);
    if (this.newProfilePic != null) {
      this.fileName = this.newProfilePic.name;
      console.log(this.fileName);

      this.fileUploadService.uploadFile(this.newProfilePic).subscribe({
        next: (response) => {
          console.log(this.fileName);
          console.log('********** FileUploadComponent.ts: File uploaded successfully: ' + response.status);

          //Updating User
          this.currentProfilePic = '/uploadedFiles/' + this.fileName;

          const updatedUser = new User(
            this.currentUser.userId,
            this.currentUser.firstName,
            this.currentUser.lastName,
            this.currentUser.username,
            this.currentUser.password,
            this.currentUser.email,
            new Date(this.currentUser.dateOfBirth),
            this.currentProfilePic, //Changed
            this.currentUser.contactNumber
          );

          this.userService.updateUser(updatedUser).subscribe({
            // eslint-disable-next-line @typescript-eslint/no-shadow
            next: (response) => {
              //Update the current User in the sessionScope will rerender the profile pic
              this.currentUser.profilePic = this.currentProfilePic;
              console.log('Successfully changed user profile pic');
            },
            error: (error) => {
              console.log('Udating user profile pic got error ' + error);
            }
          });
        },
        error: (error) => {
          this.currentProfilePic = '/uploadedFiles/' + this.fileName;
          console.log('ERROR for url: ' + this.currentProfilePic);
          console.log('********** FileUploadComponent.ts: ' + error);

          console.log(error);
        },
      });

      //Hardcoded
      //Assuming we uploaded alr, rerender the pic with new pic
      //This does not work because the above code is running async
      console.log('Successfully upload');
      this.currentProfilePic = '/uploadedFiles/' + this.fileName;
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
