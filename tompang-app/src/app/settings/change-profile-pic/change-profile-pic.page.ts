import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { SessionService } from '../../services/session.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';
import { FileUploadService } from 'src/app/services/fileUpload.service';
import { Camera } from '@awesome-cordova-plugins/camera/ngx';
import { FileTransfer, FileTransferObject, FileUploadOptions } from '@ionic-native/file-transfer/ngx';
import { CameraOptions } from '@awesome-cordova-plugins/camera/ngx';

@Component({
  selector: 'app-change-profile-pic',
  templateUrl: './change-profile-pic.page.html',
  styleUrls: ['./change-profile-pic.page.scss'],
})
export class ChangeProfilePicPage implements OnInit {
  @ViewChild('fileInput')
  fileInput: ElementRef;

  currentProfilePic: string;
  newProfilePic: File | null;
  fileName: string | null;

  imageURI: any;
  imageFileName: any;

  editError: boolean;

  currentUser: User;

  baseUrl = 'http://' + this.sessionService.ipAddress + ':8080/TompangRws/Resources/File/';

  constructor(public sessionService: SessionService,
    private userService: UserService,
    private fileUploadService: FileUploadService,
    private camera: Camera,
    private transfer: FileTransfer) { }

  ngOnInit() {
    document.getElementById('back-button').addEventListener('click', () => {
      this.resetPage();
    }, { once: true });
    this.currentUser = this.sessionService.getCurrentUser();
  }

  ionViewWillEnter() {

  }

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

          console.log(this.currentUser.dateOfBirth);

          const updatedUser = new User(
            this.currentUser.userId,
            this.currentUser.firstName,
            this.currentUser.lastName,
            this.currentUser.username,
            this.currentUser.password,
            this.currentUser.email,
            new Date(this.currentUser.dateOfBirth.toString().split('T')[0]),
            this.currentProfilePic, //Changed
            this.currentUser.contactNumber
          );

          this.doUpdateUserDetails(updatedUser);
        },
        error: (error) => {
          this.currentProfilePic = '/uploadedFiles/' + this.fileName;
          console.log('ERROR for url: ' + this.currentProfilePic);
          console.log('********** FileUploadComponent.ts: ' + error);

          console.log(error);
        },
      });

    }
  }

  doUpdateUserDetails(updatedUser: User) {
    this.userService.updateUser(updatedUser).subscribe({
            // eslint-disable-next-line @typescript-eslint/no-shadow
            next: (response) => {
              //Update the current User in the sessionScope will rerender the profile pic
              this.currentUser.profilePic = this.currentProfilePic;
              this.sessionService.setCurrentUser(this.currentUser);
              console.log('Successfully changed user profile pic');

              //reset the fileInput
              this.resetFileInput();
            },
            error: (error) => {
              console.log('Udating user profile pic got error ' + error);
            }
          });
  }

  displayProfilePic() {
    return this.sessionService.getImageBaseUrl() + this.currentUser.profilePic;
  }

  updateUserProfilePic(): void {
    console.log('Updating user profile pic...');
  }

  resetPage(): void {
    this.newProfilePic = undefined;
    this.editError = false;
  }

  resetFileInput() {
    console.log('Reseting file input');
    this.fileInput.nativeElement.value = '';

  }

  takePicture()
	{

    const options: CameraOptions = {
			quality: 50,
			destinationType: this.camera.DestinationType.FILE_URI,
			encodingType: this.camera.EncodingType.JPEG,
			mediaType: this.camera.MediaType.PICTURE,
		};

    this.camera.getPicture(options).then(async (imageData) => {
      console.log('This is the step after getting the Picture');
      this.imageURI = imageData;
      console.log('ImageURI', this.imageURI);

      await this.uploadFile();
    });

	}

  async uploadFile() {
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

    console.log(options);
    fileTransfer.upload(this.imageURI, this.baseUrl, options)
    .then((data) => {
      console.log('Uploaded Successfully', data);
      this.imageFileName = this.imageURI.split('/cache/')[1];
      this.fileName = this.imageFileName;
      //Updating User
      this.currentProfilePic = '/uploadedFiles/' + this.fileName;
      const updatedUser = new User(
        this.currentUser.userId,
        this.currentUser.firstName,
        this.currentUser.lastName,
        this.currentUser.username,
        this.currentUser.password,
        this.currentUser.email,
        new Date(this.currentUser.dateOfBirth.toString().split('T')[0]),
        this.currentProfilePic, //Changed
        this.currentUser.contactNumber
      );

      this.doUpdateUserDetails(updatedUser);
    }, (err) => {
      console.log('Error', err);
    });
  }
}
