package fi.vm.sade.eperusteet.utils.dto.peruste;

import fi.vm.sade.eperusteet.utils.dto.perusteprojekti.PerusteprojektiDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerusteHakuInternalDto extends PerusteHakuDto {
    private PerusteprojektiDto perusteprojekti;
}
