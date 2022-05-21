export class FavoritePatent {
    public id: number;
    public publicationCode: string;
    public comment?: string;
    public patentTitle: string;

    constructor(id: number, publicationCode: string, comment: string, patentTitle: string) {
      this.id = id;
      this.publicationCode = publicationCode;
      this.comment = comment;
      this.patentTitle = patentTitle;
    }
}
