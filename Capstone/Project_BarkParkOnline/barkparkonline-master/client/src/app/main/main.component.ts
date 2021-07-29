import { Component, OnInit } from '@angular/core';
import { AppService } from '../app-service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  public appService: AppService;

  constructor(appService: AppService) {
    this.appService = appService;
  }

  ngOnInit(): void {
  }

}
