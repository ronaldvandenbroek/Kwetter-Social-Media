import {UserModel} from './user.model';

export class JwtTokenModel {
  token: string;
  user: UserModel;
}
