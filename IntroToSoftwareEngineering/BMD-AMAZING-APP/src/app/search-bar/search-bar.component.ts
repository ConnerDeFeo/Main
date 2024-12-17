import { Component, OnInit } from '@angular/core';
import { debounceTime, distinctUntilChanged, Observable, startWith, Subject, switchMap } from 'rxjs';
import { NeedService } from '../need.service';
import { Need } from '../need';
import { NeedSharingService } from '../need-sharing.service';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.css'
})
export class SearchBarComponent implements OnInit{

  needs$!: Observable<Need[]>;
  private searchTerms = new Subject<string>();

  constructor(private needService: NeedService, private needSharingService: NeedSharingService) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.needs$ = this.searchTerms.pipe(
      startWith(''),
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.needService.searchNeeds(term)),
    );

    this.needs$.subscribe(needs => this.needSharingService.updateNeeds(needs));
  }

}
