import { User } from './user';

export class Kwetter {
    id: string;
    text: string;
    reports: number;
    hearts: number;
    location: string;
    owner: User;
    dateTime: Date;
}