import { Kwetter } from './kwetter';

export class User {
    id: string;
    name: string;
    bio: string;
    website: string;
    location: string;
    language: string;
    
    createdKwetters: Kwetter[];
    reportedKwetters: Kwetter[];
    heartedKwetters: Kwetter[];

    usersFollowed: User[];
    followedByUsers: User[];
}
