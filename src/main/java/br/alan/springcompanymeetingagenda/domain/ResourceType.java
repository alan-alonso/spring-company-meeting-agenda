package br.alan.springcompanymeetingagenda.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * ResourceType
 */
@Entity
@Table(name = "resource_type", schema = "meeting")
@Builder
@NoArgsConstructor
public class ResourceType extends BaseEntity {
}
