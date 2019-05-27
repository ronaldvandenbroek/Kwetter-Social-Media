import {Component, OnInit} from '@angular/core';
import {UserModel} from '../../model/user.model';
import {UserService} from '../../service/user.service';
import {AuthenticationService} from 'src/app/service/authentication.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html'
})
export class UserListComponent implements OnInit {

  users: UserModel[];

  constructor(private userService: UserService, private authenticationService: AuthenticationService) {
  }

  ngOnInit() {
    this.users = [];
    this.userService.following().subscribe(following => {
      // Get all users
      this.userService.findAll().subscribe(allUsers => {
        // Loop though all users
        allUsers.forEach(user => {
          // Check if users is not logged in user
          if (user.uuid !== this.authenticationService.currentLoginUser.uuid) {
            // Loop through all users the logged in user follows
            following.forEach(followedUser => {
              // Check if the logged in user is following the user
              if (followedUser.uuid === user.uuid) {
                user.followed = true;
              }
            });
            this.users.push(user);
          }
        });
      });
    });
  }

  public follow(user: UserModel) {
    this.userService.follow(user).subscribe(data => {
      this.ngOnInit();
    });
  }

  public unfollow(user: UserModel) {
    this.userService.unfollow(user).subscribe(data => {
      this.ngOnInit();
    });
  }
}
