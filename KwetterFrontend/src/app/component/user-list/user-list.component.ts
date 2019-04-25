import { Component, OnInit } from '@angular/core';
import { User } from '../../model/user';
import { UserService } from '../../service/user.service';
import { JwtToken } from 'src/app/model/jwt-token';
import { AuthenticationService } from 'src/app/service/authentication.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[];
  user: User;

  constructor(private userService: UserService, private authenticationService: AuthenticationService) {
    this.users = [];
  }

  ngOnInit() {
    this.userService.findAll().subscribe(data => {
      data.forEach(user => {
        console.log("Checking follows");
        console.log(user.name);

        if(user.id !== this.authenticationService.currentLoginUser.id) {
          user.usersFollowed.forEach(followedUser => {
            if (followedUser.id == this.authenticationService.currentLoginUser.id) {
              user.followed = true;
              console.log("Is followed: " + user.followed);
            }

          });
          console.log("Adding user:")
          console.log(user.name)
          this.users.push(user)
        }
      });
    });
  }
}