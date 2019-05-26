import {Link} from './link';

export class User {
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

  links: Link[];
}
