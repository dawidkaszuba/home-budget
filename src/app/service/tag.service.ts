import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ToolService } from '../tool.service';
import { Observable } from 'rxjs';
import { Tag } from 'src/model/Tag';

@Injectable({
  providedIn: 'root'
})
export class TagService {

  constructor(private httpService: HttpClient, private toolService: ToolService)  { }

  public getAllTags(kind: string): Observable<Array<Tag>> {
    const params = new HttpParams().set('kind', kind);
    return this.httpService.get<Array<Tag>>('http://localhost:8080/users/' +
    this.toolService.getUserIdFromLocalStorage() + '/tags', {params});
  }
}
