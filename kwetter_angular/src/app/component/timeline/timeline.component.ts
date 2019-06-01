import {Component, OnInit} from '@angular/core';
import {KwetterModel} from '../../model/kwetter.model';
import {KwetterService} from '../../service/kwetter.service';
import {StompConfig, StompService} from '@stomp/ng2-stompjs';
import {Message} from '@stomp/stompjs';
import {Observable} from 'rxjs';
import {AlertService} from '../../service/alert.service';
import {AuthenticationService} from '../../service/authentication.service';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html'
})
export class TimelineComponent implements OnInit {
  kwetters: KwetterModel[];
  private serverUrl = 'ws://localhost:8080/socket';
  private stompService: StompService;
  private messages: Observable<Message>;

  constructor(private timelineService: KwetterService,
              private alertService: AlertService,
              private authenticationService: AuthenticationService) {
    this.initializeWebSocketConnection();
  }

  ngOnInit() {
    this.timelineService.timeline().subscribe(data => {
      this.kwetters = data;
    });
  }

  initializeWebSocketConnection() {
    const stompConfig: StompConfig = {
      url: this.serverUrl,
      headers: {
        login: '',
        passcode: ''
      },
      heartbeat_in: 0,
      heartbeat_out: 20000,
      reconnect_delay: 5000,
      debug: true
    };
    this.stompService = new StompService(stompConfig);
    this.messages = this.stompService.subscribe(
      '/timeline/' + this.authenticationService.currentLoginUser.uuid
    );
    this.messages.subscribe((message: Message) => {
      this.alertService.success(message.body);
      this.ngOnInit();
    });
  }
}
