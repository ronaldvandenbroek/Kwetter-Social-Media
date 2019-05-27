import {LinkModel} from './link.model';

export class UserModel {
  uuid: string;
  name: string;
  bio: string;
  website: string;
  location: string;
  language: string;

  createdKwetterAmount: number;
  reportedKwetterAmount: number;
  heartedKwetterAmount: number;
  usersFollowedAmount: number;
  followedByUsersAmount: number;

  followed: boolean;

  links: LinkModel[];
}
