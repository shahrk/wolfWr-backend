package com.ncsu.wolfwr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ncsu.wolfwr.entity.BillingInfo;

@Repository
public interface BillingInfoRepository extends JpaRepository<BillingInfo, Integer>{

	Optional<BillingInfo> findByShipmentId(Integer shipmentId);
	
	@Query("SELECT sum(b.amount) from SupplierShipment ss, Shipment s, BillingInfo b where ss.shipmentId = b.shipmentId and ss.shipmentId = s.shipmentId and b.paymentStatus = 0 and ss.supplierId = :supplierId and s.recepientStoreId = :storeId group by ss.supplierId")
	Optional<Float> getSupplierPendingPaymentForStore(@Param("supplierId") int supplierId, @Param("storeId") int storeId);
	
	@Query("SELECT sum(b.amount) from SupplierShipment ss, Shipment s, BillingInfo b where ss.shipmentId = b.shipmentId and ss.shipmentId = s.shipmentId and b.paymentStatus = 0 and ss.supplierId = :supplierId group by ss.supplierId")
	Optional<Float> getSupplierPendingPayment(@Param("supplierId") int supplierId);
	
	@Query("SELECT sum(b.amount) from StoreShipment ss, Shipment s, BillingInfo b where ss.shipmentId = b.shipmentId and ss.shipmentId = s.shipmentId and b.paymentStatus = 0 and s.recepientStoreId = :fromStoreId and ss.senderStoreId = :toStoreId")
	Optional<Float> getStorePendingPaymentForStore(@Param("toStoreId") int toStoreId, @Param("fromStoreId") int fromStoreId);
}
