import {User} from './user';
import {Link} from './link';

export class Kwetter {
  id: string;
  text: string;
  reports: number;
  hearts: number;
  location: string;
  owner: User;
  dateTime: string;

  links: Link[];
}
