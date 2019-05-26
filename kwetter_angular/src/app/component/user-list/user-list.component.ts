import {Component, OnInit} from '@angular/core';
import {User} from '../../model/user';
import {UserService} from '../../service/user.service';
import {AuthenticationService} from 'src/app/service/authentication.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[];

  constructor(private userService: UserService, private authenticationService: AuthenticationService) {
  }

  ngOnInit() {
    this.users = [];
    this.userService.following().subscribe(following => {
      console.log('following');
      console.log(following);
      // Get all users
      this.userService.findAll().subscribe(allUsers => {
        // console.log('allUsers');
        // console.log(allUsers);
        // Loop though all users
        allUsers.forEach(user => {
          // console.log("Checking follows");
          // console.log(user.name);

          // console.log(user.uuid);
          // console.log(this.authenticationService.currentLoginUser.uuid);

          // Check if users is not logged in user
          if (user.uuid !== this.authenticationService.currentLoginUser.uuid) {
            // console.log('Test');
            // Loop through all users the logged in user follows
            following.forEach(followedUser => {
              // Check if the logged in user is following the user
              if (followedUser.uuid === user.uuid) {
                user.followed = true;
                // console.log("Is followed: " + user.followed);
              }
            });
            // console.log("Adding user:");
            // console.log(user.name);
            this.users.push(user);
          }
        });
      });
    });
  }

  public follow(user: User) {
    console.log('following user');
    console.log(user);
    this.userService.follow(user).subscribe(data => {
      this.ngOnInit();
    });
  }

  public unfollow(user: User) {
    console.log('unfollowing user');
    console.log(user);
    this.userService.unfollow(user).subscribe(data => {
      this.ngOnInit();
    });
  }
}
