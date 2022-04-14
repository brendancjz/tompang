import { User } from './user';

export class UpdateUserReq {
  username: string | undefined;
  password: string | undefined;
  user: User | undefined;
  newPassword: string | undefined;


  constructor(username?: string, password?: string, user?: User, newPassword?: string) {
    this.username = username;
    this.password = password;
    this.user = user;
    this.newPassword = newPassword;
  }
}
