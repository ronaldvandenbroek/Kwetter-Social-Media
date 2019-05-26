import {UserModel} from './user.model';
import {LinkModel} from './link.model';

export class KwetterModel {
  id: string;
  text: string;
  reports: number;
  hearts: number;
  location: string;
  owner: UserModel;
  dateTime: string;

  links: LinkModel[];
}
