import { Component, OnInit } from '@angular/core';
import { User } from '../../model/user';
import { UserService } from '../../service/user.service';
import { JwtToken } from 'src/app/model/jwt-token';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { ThrowStmt } from '@angular/compiler';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[];
  loggedInUser: User;

  constructor(private userService: UserService, private authenticationService: AuthenticationService) {
  }

  ngOnInit() {
    this.users = [];
    this.userService.profile().subscribe(data => {
        this.loggedInUser =   data;
    });

    this.loggedInUser = this.authenticationService.currentLoginUser;
    //Get all users
    this.userService.findAll().subscribe(data => {
      //Loop though all users
      data.forEach(user => {
        //console.log("Checking follows");
        //console.log(user.name);

        //Check if users is not logged in user
        if(user.id !== this.loggedInUser.id) {
          //Loop through all users the logged in user follows
          this.loggedInUser.usersFollowed.forEach(followedUser => {
            //Check if the logged in user is following the user
            if (followedUser.id == user.id) {
              user.followed = true;
              //console.log("Is followed: " + user.followed);
            }
          });
          //console.log("Adding user:")
          //console.log(user.name)
          this.users.push(user)
        }
      });
    });
  }

  public follow(user: User) {
    console.log("following user")
    console.log(user)
    this.userService.follow(user).subscribe(data => {
      this.ngOnInit();
    });
  }

  public unfollow(user: User) {
    console.log("unfollowing user")
    console.log(user)
    this.userService.unfollow(user).subscribe(data => {
      this.ngOnInit();
    });
  }
}